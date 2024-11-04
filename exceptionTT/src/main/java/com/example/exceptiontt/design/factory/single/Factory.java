package com.example.exceptiontt.design.factory.single;

import com.example.exceptiontt.design.factory.single.entity.HuaweiS;
import com.example.exceptiontt.design.factory.single.entity.Phone;
import com.example.exceptiontt.design.factory.single.entity.XiaomiS;

public class Factory {
//    private Phone phone;
//    public Factory(Phone phone){
//        this.phone = phone;
//    }

    public static Phone getPhone(String phone) {
        if ("小米".equals(phone)){
            return new XiaomiS();
        }else if ("华为".equals(phone)){
            return new HuaweiS();
        }
        return null;
    }
}
