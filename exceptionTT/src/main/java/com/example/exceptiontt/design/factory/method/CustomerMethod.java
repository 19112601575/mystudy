package com.example.exceptiontt.design.factory.method;

import com.example.exceptiontt.design.factory.method.factory.entity.XiaomiFactory;
import com.example.exceptiontt.design.factory.method.phone.entity.Phone;

public class CustomerMethod {
    public static void main(String[] args) {
        //区别于简单工厂模式，需要什么创建对应对象的工厂获得实例。
        //摒弃了在工厂内部进行指向创建对象的判断，解耦。提高了扩展性，但增加了代码量。
        XiaomiFactory xiaomiFactory = new XiaomiFactory();
        Phone phone = xiaomiFactory.getPhoneInstance();
        phone.getPhone();

        //抽象工厂模式，区别方法工厂模式在于产品族概念，例如：手机配手机壳，手机配耳机，产品族一起在工厂中实现。
    }
}
