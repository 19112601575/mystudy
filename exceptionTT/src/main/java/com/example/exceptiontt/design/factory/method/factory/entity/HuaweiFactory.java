package com.example.exceptiontt.design.factory.method.factory.entity;

import com.example.exceptiontt.design.factory.method.phone.entity.HuaweiM;
import com.example.exceptiontt.design.factory.method.phone.entity.Phone;

public class HuaweiFactory implements FactoryM {

    @Override
    public Phone getPhoneInstance() {
        return new HuaweiM();
    }
}
