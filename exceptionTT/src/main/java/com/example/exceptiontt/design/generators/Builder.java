package com.example.exceptiontt.design.generators;

/**
 * 创建模式：它主要关注于对象的创建过程。这些模式提供了一种在创建对象时隐藏创建逻辑的方式，使得程序在判断需要创建哪些对象时更加灵活。
 * 建造者模式（生成器模式）：
 * 需求场景：1、对象创建流程复杂，2、需要一个类的不同对象
 * 1：流程必须复杂：建造者模式本就复杂，流程必须比建造者模式复杂
 * 2、需要一个类的不同对象：若对象相同，只需要执行1、2并将对象封装即可
 *
 * 模式属性：
 * Build：接口，定义抽象方法
 * ConcreteBuild：具体方法实现
 * Directors：提供不同对象的创建
 * Clients：获取不同对象
 */
public interface Builder {
    void makeTires();

    void makeGlass();

    Cars getCars();
//    public String domesticTire(){
//        return "国产轮胎";
//    }

//    public String germanTires(){
//        return "德国轮胎";
//    }

//    public String domesticGlass(){
//        return "国产玻璃";
//    }
//    public String germanGlass(){
//        return "德国玻璃";
//    }
}
