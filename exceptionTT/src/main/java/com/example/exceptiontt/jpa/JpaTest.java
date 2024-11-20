package com.example.exceptiontt.jpa;

import com.example.exceptiontt.jpa.entityJpa.UserJpa;
import com.example.exceptiontt.jpa.mapperJpa.UserMapperJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JpaTest {
    @Autowired
    private UserMapperJpa userMapperJpa;
    @RequestMapping("/jpa")
    public void jpaTest(){
        UserJpa user1 = userMapperJpa.findByUserName("user1");
        System.out.println(user1);
    }
}
