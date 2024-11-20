package com.example.demo.pojo;

import lombok.Data;

@Data
public class Users {
    private int id;
    private String username;
    private String password;
    private String email;
    private String createdAt;
}
