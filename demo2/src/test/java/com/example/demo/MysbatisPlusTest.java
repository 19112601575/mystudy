package com.example.demo;

import com.example.demo.mapper.UserMapper;
import com.example.demo.po.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@SpringBootTest
public class MysbatisPlusTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectUser(){
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);

    }
}
