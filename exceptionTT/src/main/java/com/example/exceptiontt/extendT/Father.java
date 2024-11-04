package com.example.exceptiontt.extendT;

import lombok.Data;

@Data
public class Father {
    private String name;
    private int age;

    public void getCar(){
        System.out.println("父亲的车");
    }
}
