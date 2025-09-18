/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.repositories;

import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Payment;
import com.vvmntl.pojo.PaymentMethod;
import java.util.Map;

/**
 *
 * @author locnguyen
 */
public interface PaymentRepository {
    String createPaymentUrl(Appointment appointment, PaymentMethod paymentMethod);
    void handlePaymentCallback(Map<String, String> params);
    Payment save(Payment p);
    Payment findByTransactionId(String transactionId);
}
