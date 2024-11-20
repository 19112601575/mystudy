package com.example.exceptiontt.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@TableName("users")
public class User {
    @TableField("id")
    private int id;

    @TableField("username")
    private String userName;

    @TableField("age")
    private int age;

    @TableField("password")
    private String password;

    @TableField("email")
    private String email;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }


}
