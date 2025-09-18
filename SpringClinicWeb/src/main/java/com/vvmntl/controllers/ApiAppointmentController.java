/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.Patient;
import com.vvmntl.pojo.Service;
import com.vvmntl.pojo.StatusEnum;
import com.vvmntl.pojo.User;
import com.vvmntl.services.AppointmentService;
import com.vvmntl.services.AppointmentSlotService;
import com.vvmntl.services.DoctorService;
import com.vvmntl.services.EmailService;
import com.vvmntl.services.PatientService;
import com.vvmntl.services.ServiceService;
import com.vvmntl.services.UserService;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author locnguyen
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiAppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentSlotService slotService;

    @Autowired
    private UserService userService;
    @Autowired
    private ServiceService serService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private EmailService emailService;
    
    @DeleteMapping("/secure/appointments/{appointmentId}")
    public ResponseEntity<?> cancelAppointment(@PathVariable("appointmentId") int appointmentId, Principal principal) {
        try {
            User currentUser = this.userService.getUserByUsername(principal.getName());
            Appointment appointment = this.appointmentService.getAppointmentById(appointmentId);

            if (!Objects.equals(appointment.getPatientId().getId(), currentUser.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền thực hiện hành động này.");
            }
            
            if (!"PENDING".equals(appointment.getStatus())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chỉ có thể hủy lịch hẹn đang ở trạng thái chờ.");
            }

            boolean isDeleted = this.appointmentService.deleteAppointment(appointmentId);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy lịch hẹn để xóa.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server: " + e.getMessage());
        }
    }

    @GetMapping("/available-slots")
    public ResponseEntity<List<Appointmentslot>> getAvailableSlots(@RequestParam Map<String, String> params) {
        try {
            List<Appointmentslot> availableSlots = this.slotService.getAvailableSlots(params);
            return new ResponseEntity<>(availableSlots, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/secure/patients/appointments/{slotId}")
    public ResponseEntity<?> bookAppointment(@RequestBody Appointment appoinment, Principal principal, @PathVariable(value="slotId") int slotId) {
        try{
            Service service = this.serService.getServiceById(appoinment.getServiceId().getId());
            if(service == null){
                return ResponseEntity.badRequest().body("Không tìm thấy dịch vụ!");
            }
            Appointmentslot appSlot = this.slotService.getSlotById(slotId);
            if(Boolean.TRUE.equals(appSlot.getIsBooked())){
                return ResponseEntity.badRequest().body("Lịch này đã được đặt vui lòng chọn lịch khác!" + appSlot.getIsBooked());
            }
            User user = this.userService.getUserByUsername(principal.getName());
            
            Patient patient = patientService.getPatientById(user.getId());
            if (patient == null) {
                return ResponseEntity.badRequest().body("Không tìm thấy hồ sơ bệnh nhân!");
            }
            appoinment.setPatientId(patient);
            appoinment.setServiceId(service);
            appoinment.setAppointmentSlot(appSlot);
            appoinment.setCreatedDate(LocalDate.now());
            appoinment.setStatus(StatusEnum.PENDING);
            if(Boolean.TRUE.equals(appoinment.getOnline())){
                appoinment.setRoomUrl("https://meet.jit.si/" + appoinment.getAppointmentSlot().getId() 
                + "-" + appoinment.getPatientId().getId() + "-" + appoinment.getServiceId().getName());
           }
            appoinment.setPaymentForPrescription(Boolean.FALSE);
            
            slotService.updateSlot(appSlot);
            String subject = "THÔNG TIN LỊCH HẸN";
            StringBuilder content = new StringBuilder();
            content.append("Xin chào ")
                    .append(appoinment.getPatientId().getUser().getLastName())
                    .append(" ")
                    .append(appoinment.getPatientId().getUser().getFirstName())
                    .append(",\n\n")
                    .append("Lịch đặt của bạn đã được lưu trong hệ thống!\n")
                    .append("Ngày khám: ")
                    .append(appoinment.getAppointmentSlot().getScheduleId().getDateWork())
                    .append("\n\n")
                    .append("Thời gian khám: ")
                    .append(appoinment.getAppointmentSlot().getStartTime())
                    .append(" đến ")
                    .append(appoinment.getAppointmentSlot().getEndTime())
                    .append("\n\n");
            if (Boolean.TRUE.equals(appoinment.getOnline()) && appoinment.getRoomUrl() != null) {
                content.append("Phòng khám online: ")
                        .append(appoinment.getRoomUrl())
                        .append("\n\n");
            }
            content.append("Trân trọng,\n")
                    .append("Phòng khám");
            String emailTo = appoinment.getPatientId().getUser().getEmail();
            Appointment appt = this.appointmentService.addAppointment(appoinment);
            this.emailService.sendSimpleEmail(emailTo, subject, content.toString());
            appSlot.setIsBooked(true);
            return ResponseEntity.status(HttpStatus.CREATED).body(appt);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/secure/appointments/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentDetails(@PathVariable("appointmentId") int appointmentId) {
        try {
            Appointment appointment = this.appointmentService.getAppointmentById(appointmentId);
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/appoimentslots/{doctorId}")
    public ResponseEntity<?> list(@PathVariable(value = "doctorId") int id){
        try {
            Doctor doctor = this.doctorService.getDoctorById(id);
            return ResponseEntity.ok(this.slotService.getListAppointmentSlotByDoctorId(doctor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/secure/appointments")
    public ResponseEntity<List<Appointment>> loadAppointments(@RequestParam Map <String, String> params){
        
        return ResponseEntity.ok(this.appointmentService.loadAppointments(params));
    }
}