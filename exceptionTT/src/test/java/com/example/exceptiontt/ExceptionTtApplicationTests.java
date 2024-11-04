package com.example.exceptiontt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.exceptiontt.mybatisplus.entity.User;
import com.example.exceptiontt.mybatisplus.service.impl.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootTest
@ComponentScan("com.example.exceptiontt")
class ExceptionTtApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void licenseTest() {
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(User::getId,1);
//        List<User> list = userService.list(queryWrapper);
//        list.forEach(System.out::println);
        long count = userService.count();
        List<User> list = userService.list();
        list.forEach(System.out::println);
        System.out.println(count);
    }

}
