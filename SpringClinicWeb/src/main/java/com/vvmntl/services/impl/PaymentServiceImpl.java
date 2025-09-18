/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.configs.PaymentConfig;
import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Payment;
import com.vvmntl.pojo.PaymentMethod;
import com.vvmntl.pojo.PaymentStatus;
import com.vvmntl.repositories.AppointmentRepository;
import com.vvmntl.repositories.PaymentRepository;
import com.vvmntl.services.PaymentService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author locnguyen
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepo;
    @Autowired
    private AppointmentRepository appointmentRepo;

    private String createVnpayUrl(Payment payment) throws Exception {
        long amount = payment.getTotalAmount().longValue() * 100;

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", PaymentConfig.VNPAY_TMN_CODE);
        vnpParams.put("vnp_Amount", String.valueOf(amount));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", payment.getTransactionId()); // Dùng transactionId đã tạo
        vnpParams.put("vnp_OrderInfo", "Thanh toan lich hen " + payment.getAppointmentId().getId());
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", PaymentConfig.RETURN_URL);
        vnpParams.put("vnp_IpAddr", "127.0.0.1"); // Nên lấy IP động
        vnpParams.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        StringBuilder signData = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            signData.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII.toString())).append("&");
        }
        signData.deleteCharAt(signData.length() - 1);
        
        String signature = Hex.encodeHexString(HmacUtils.hmacSha512(PaymentConfig.VNPAY_HASH_SECRET, signData.toString()));
        vnpParams.put("vnp_SecureHash", signature);

        StringBuilder query = new StringBuilder(PaymentConfig.VNPAY_ENDPOINT).append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII.toString()))
                 .append("=").append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII.toString())).append("&");
        }
        query.deleteCharAt(query.length() - 1);
        return query.toString();
    }

    @Override
    public void handlePaymentCallback(Map<String, String> params) {
        String transactionId = params.get("vnp_TxnRef");
        Payment payment = paymentRepo.findByTransactionId(transactionId);

        if (payment != null) {

            String responseCode = params.get("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                payment.setStatus(PaymentStatus.SUCCESSFUL);
                
                Appointment app = payment.getAppointmentId();
//                app.setStatus("CONFIRMED");
                app.setPaymentForService(true);
                appointmentRepo.addAppointment(app);
            } else {
                payment.setStatus(PaymentStatus.FAILED);
            }
            paymentRepo.save(payment);
        }
    }

    @Override
    public String generatePaymentUrl(Payment payment, PaymentMethod provider) {
        try {
            if (provider == PaymentMethod.VNPAY) {
                return createVnpayUrl(payment);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi khi tạo URL thanh toán: " + ex.getMessage());
        }
        return null;
    }
}
