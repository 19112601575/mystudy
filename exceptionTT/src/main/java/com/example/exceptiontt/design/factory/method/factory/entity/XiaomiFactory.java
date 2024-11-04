package com.example.exceptiontt.design.factory.method.factory.entity;

import com.example.exceptiontt.design.factory.method.phone.entity.Phone;
import com.example.exceptiontt.design.factory.method.phone.entity.XiaomiM;

public class XiaomiFactory implements FactoryM {
    @Override
    public Phone getPhoneInstance() {
        return new XiaomiM();
    }
}
