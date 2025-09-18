import axios from "axios";
import cookie from 'react-cookies'

const BASE_URL = "http://localhost:8080/SpringClinicWeb/api";

export const endpoints = {
    "services": "/services",

    //specialize
    "specializes": "/specializes",

    //doctor
    "doctors": "/doctors",
    "doctorsDetail": (userId) => `/doctors/${userId}`,
    "addDoctorProfile": (userId) => `/doctors/${userId}/add-profile`,
    "choiceSpecialize": (userId) => `/doctors/${userId}/specializes`,

    //user
    "users": "/users",
    "login": "/login",
    "profile": "/secure/profile",

    //appointment
    "available-slots": "/available-slots",
    "bookAppointment": (slotId) => `/secure/patients/appointments/${slotId}`,
    "myAppointments": "/secure/appointments",
    "bookAndPay": "/secure/appointments/book-and-pay",
    "deleteAppointment": (appointmentId) => `/secure/appointments/${appointmentId}`,
    "appointmentslots": (doctorId) => `/appoimentslots/${doctorId}`,
    "appointmentslotsBySchedule": (scheduleId) => `/appointmentslots?scheduleId=${scheduleId}`,
    "appointmentDetail": "/secure/appointments",
    

    //workschedule
    "workschedules":(doctorId) => `/workschedules/${doctorId}`,
    "createWorkSchedules": "/secure/workschedules",
    "deleteWorkSchedules": (wsId) => `/secure/workschedules/delele/${wsId}`,
    //medicalRecord
    "createMedicalRecord":(appointmentId) => `/secure/medicalrecords/${appointmentId}`,
    "listMedicalRecord": `secure/patients/medicalRecords`,
    "medicalDetail":(medicalId) => `/secure/medicalrecords/${medicalId}`,
    "medicalRedcords": "/secure/medicalrecords",

};

export const authApis = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': `Bearer ${cookie.load('token')}`
        }
    })
}

export default axios.create({
    baseURL: BASE_URL
})