package com.example.exceptiontt.design.strategy;

public class Shopping {
    private PaymentStrategy paymentStrategy;

    public Shopping(PaymentStrategy paymentStrategy){
        this.paymentStrategy = paymentStrategy;
    }

    //付款
    public void checkout(int amount){
        paymentStrategy.pay(amount);
    }
}
