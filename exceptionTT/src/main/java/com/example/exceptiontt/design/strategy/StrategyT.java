package com.example.exceptiontt.design.strategy;

public class StrategyT {
    public static void main(String[] args) {
        Shopping shopping = new Shopping(new AlipayPayment());
        shopping.checkout(100);
    }
}
