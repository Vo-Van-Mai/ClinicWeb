/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.services;

import com.vvmntl.pojo.Payment;
import com.vvmntl.pojo.PaymentMethod;
import java.util.Map;

/**
 *
 * @author locnguyen
 */
public interface PaymentService {
    String generatePaymentUrl(Payment payment, PaymentMethod provider);
    void handlePaymentCallback(Map<String, String> params);
}
