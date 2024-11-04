package com.example.exceptiontt.design.proxy.dynamic;

import com.example.exceptiontt.design.proxy.staticP.Landlord;
import com.example.exceptiontt.design.proxy.staticP.Rent;

public class DynamicProxyT {
    public static void main(String[] args) {
        Landlord landlord =new Landlord();
        DynamicProxy dynamicProxy = new DynamicProxy(landlord);
        Rent proxy = (Rent)dynamicProxy.getProxy();
        proxy.rent();
    }
}
