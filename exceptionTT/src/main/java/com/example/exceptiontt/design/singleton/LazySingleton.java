package com.example.exceptiontt.design.singleton;

public class LazySingleton {

    private LazySingleton(){}
    private static LazySingleton lazySingleton;

    //懒汉需要考虑线程安全
    public static synchronized LazySingleton getInstance(){
        if (lazySingleton == null){
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}
