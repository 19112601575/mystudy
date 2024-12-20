package com.example.exceptiontt.jpa;

import com.example.exceptiontt.jpa.entityJpa.UserJpa;
import com.example.exceptiontt.jpa.mapperJpa.UserMapperJpa;
import com.example.exceptiontt.mybatisplus.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class JpaTest {
    @Autowired
    private UserMapperJpa userMapperJpa;
    @RequestMapping("/jpa")
    public void jpaTest(){
        UserJpa user1 = userMapperJpa.findByUserName("user1");
        System.out.println(user1);

        //更新方式一
        user1.setUserName("user11");
        userMapperJpa.save(user1);

        //更新方式二
        Optional<UserJpa> byId = userMapperJpa.findById(2);
        if (byId.isPresent()){
            byId.get().setUserName("user22");
            userMapperJpa.save(byId.get());
        }else {
            System.out.println("数据不存在");
        }

    }

    public static UserJpa getUserJpa(){
        UserJpa userJpa = new UserJpa();
        userJpa.setUserName("jack");
        userJpa.setAge(18);
        return userJpa;
    }
}
