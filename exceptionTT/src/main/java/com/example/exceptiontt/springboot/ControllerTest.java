package com.example.exceptiontt.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {
    @RequestMapping("/mytest/hi")
    public void getHi(){
        System.out.println("你好");
    }
}
