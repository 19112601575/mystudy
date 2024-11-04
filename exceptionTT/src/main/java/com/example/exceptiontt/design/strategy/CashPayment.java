package com.example.exceptiontt.design.strategy;

public class CashPayment implements PaymentStrategy{
    @Override
    public void pay(int amount) {
        System.out.println("现金支付" + amount);
    }
}
