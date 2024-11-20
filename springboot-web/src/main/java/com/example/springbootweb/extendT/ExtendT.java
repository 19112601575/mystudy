package com.example.springbootweb.extendT;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class ExtendT {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        B b = new B();
        C c = new C();

        b.setAge(12);
        b.setName("sad");
        b.setFirm("asdasd");
        BeanUtils.copyProperties(c,b);
        System.out.println(c);
    }
}
