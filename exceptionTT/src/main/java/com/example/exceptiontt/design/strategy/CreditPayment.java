package com.example.exceptiontt.design.strategy;

public class CreditPayment implements PaymentStrategy{
    @Override
    public void pay(int amount) {
        System.out.println("银行卡支付" + amount);
    }
}
