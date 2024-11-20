package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.Users;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@MapperScan("com.example.demo.mapper")
public class MyTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void selecTest(){
        userMapper.selectList(null).forEach(System.out::println);
    }

    @Test
    public void test01(){
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username","user1");
        List<Users> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    @Test
    public void test02(){
        //将(年龄大于20并且用户名中含有a)或邮箱为null的用户信息修改
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age",20)
                .like("username","a")
                .or()
                .isNull("email");
        Users users = new Users();
        users.setUsername("小明");
        users.setEmail("123@163.com");
        int result = userMapper.update(users,queryWrapper);
        System.out.println("result"+result);
    }
    //对前端用户传入的数据进行检查：姓名不为空，年龄在20-30之间
    @Test
    public void test03(){
        String name ="";
        int age = 0 ;
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)){
            queryWrapper.like("username","张");
        }
        if (age != 0){
            queryWrapper.gt("age",30)
                    .ge("age",20);
        }

        queryWrapper.like(StringUtils.isNotBlank(name),"username","张")
                .ge(age != 0,"age",20)
                .le(age != 0,"age",30);

        List<Users> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
