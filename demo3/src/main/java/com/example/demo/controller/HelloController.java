package com.example.demo.controller;

import com.mysql.cj.log.LogFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j//Lombok引入注解，直接使用log
@RestController()
public class HelloController {

//    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/h")
    public String hello(){
//        logger.info("哈哈哈");
        log.info("啊啊啊");
        return "hello";
    }
}
