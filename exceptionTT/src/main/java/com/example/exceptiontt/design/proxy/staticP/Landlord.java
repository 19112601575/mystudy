package com.example.exceptiontt.design.proxy.staticP;

public class Landlord implements Rent{
    @Override
    public void rent() {
        System.out.println("出租");
    }
}
