package com.example.exceptiontt.design.proxy.dynamic;

import com.example.exceptiontt.design.proxy.staticP.Landlord;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler {
    private Landlord landlord;

    public DynamicProxy(Landlord landlord1){
        this.landlord = landlord1;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader()
                , landlord.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(landlord, args);
        return invoke;
    }
}
