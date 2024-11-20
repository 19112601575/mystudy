package com.example.springbootweb.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

@Controller
public class TestController {

    @RequestMapping("/test")//没有指定请求类型时，接受所有类型的请求
    @ResponseBody//将Java对象转换为json格式的数据，存入到html response body中
    public String test(User user, MultipartFile pic) throws IOException {
//        username：前端输入的数据
        System.out.println("user："+ user);
        System.out.println(pic.getSize());
        return "收到数据";
    }

    @RequestMapping("session")
    @ResponseBody
    //session
    public String s1(HttpSession session,String name){
        session.setAttribute("name",name);
        return "数据已存储";
    }
    static class User{
        private String username;
        private String password;
        private int id;
        //指定日期格式
        @DateTimeFormat(pattern = "yyyy-MM-dd")
//        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthday;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public LocalDate getBirthday() {
            return birthday;
        }

        public void setBirthday(LocalDate birthday) {
            this.birthday = birthday;
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", id=" + id +
                    ", birthday=" + birthday +
                    '}';
        }
    }


}
