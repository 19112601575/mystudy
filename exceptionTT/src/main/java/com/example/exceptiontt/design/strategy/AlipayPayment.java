package com.example.exceptiontt.design.strategy;

public class AlipayPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("支付宝支付" + amount);
    }
}
