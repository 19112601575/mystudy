package com.example.exceptiontt.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.exceptiontt.mybatisplus.dao.UserMapper;
import com.example.exceptiontt.mybatisplus.entity.User;
import com.example.exceptiontt.mybatisplus.service.impl.UserService;
import com.example.exceptiontt.mybatisplus.service.impl.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class MybatisT {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/mybatisplus")
    public void mybatisTest(){
        int id =1;
        //mybatis-plus使用userMapper查询
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("id",id);
//        List<User> list = userService.list(queryWrapper);
//        list.forEach(System.out::println);

        //IService使用userService查询
        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id",id);
        List<User> list2 = userMapper.selectList(queryWrapper2);
        list2.forEach(System.out::println);

    }
}
