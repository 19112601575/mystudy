package com.example.exceptiontt.mybatisplus.service.impl.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exceptiontt.mybatisplus.dao.UserMapper;
import com.example.exceptiontt.mybatisplus.entity.User;
import com.example.exceptiontt.mybatisplus.service.impl.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
