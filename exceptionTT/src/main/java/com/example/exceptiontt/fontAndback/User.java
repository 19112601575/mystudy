package com.example.exceptiontt.fontAndback;

import lombok.Data;

@Data
public class User {
    private String name;
    private Integer age;

    public User(String name, Integer userId) {
        this.name = name;
        this.age = userId;
    }
}
