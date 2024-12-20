package com.example.exceptiontt.fontTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.exceptiontt.mybatisplus.dao.UserMapper;
import com.example.exceptiontt.mybatisplus.entity.User;
import com.example.exceptiontt.mybatisplus.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FontTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public void login(@RequestParam String email, @RequestParam String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        List<User> list = userService.list(queryWrapper);
        if (!list.isEmpty()){
            throw new RuntimeException("邮箱已存在");
        }else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setUserName(email);
            userService.save(user);
        }
    }
}
