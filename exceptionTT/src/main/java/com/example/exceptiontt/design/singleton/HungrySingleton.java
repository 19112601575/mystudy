package com.example.exceptiontt.design.singleton;

public class HungrySingleton {
    //private的构造器，避免被外部实例化
    private HungrySingleton(){}
    //饿汉
    private static HungrySingleton taskManager = new HungrySingleton();

    public static HungrySingleton getInstance(){
        return taskManager;
    }
}
