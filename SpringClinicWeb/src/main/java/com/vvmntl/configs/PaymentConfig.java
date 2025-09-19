/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.configs;

/**
 *
 * @author locnguyen
 */
public class PaymentConfig {
    public static final String VNPAY_TMN_CODE = "ZBUYLH19";
    public static final String VNPAY_HASH_SECRET = "JRNJBLCGEILFLER9ZIRL4BH1FP5ULNTD";
    public static final String VNPAY_ENDPOINT = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    // MoMo - Thay bằng thông tin của bạn
    // public static final String MOMO_PARTNER_CODE = "MOMO";
    // public static final String MOMO_ACCESS_KEY = "F8BBA842ECF85";
    // public static final String MOMO_SECRET_KEY = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
    // public static final String MOMO_ENDPOINT = "https://test-payment.momo.vn/v2/gateway/api/create"; 

    public static final String RETURN_URL = "http://localhost:3000/payment-return";
    public static final String NOTIFY_URL = "http://localhost:8080/SpringClinicWeb/api/payment/notify";
}
