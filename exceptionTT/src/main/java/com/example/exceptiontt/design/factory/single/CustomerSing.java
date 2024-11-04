package com.example.exceptiontt.design.factory.single;

import com.example.exceptiontt.design.factory.single.entity.Phone;

public class CustomerSing {
    public static void main(String[] args) {
//        Factory factory = new Factory();
//        Phone xiaomi = factory.getPhone("小米");
//        xiaomi.getPhone();
        Factory.getPhone("小米").getPhone();
    }
}
