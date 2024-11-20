package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.po.User;
import com.example.demo.service.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class serviceImpl implements service {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void selectById(String id) {
        User user = userMapper.selectById(id);
        System.out.println(user.toString());
    }
}
