package com.example.exceptiontt.lambda;

import lombok.Data;

@Data
public class Person {
    private String name;
    private Integer salary;
    private Integer age;
    private String sex;
    private String address;

    //@Data只有默认无参构造器
    public Person(String name, Integer salary, Integer age, String sex, String address) {
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }
}
