package com.example.exceptiontt.mock;

import com.example.exceptiontt.exception.Resp;
import com.example.exceptiontt.lambda.Person;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockMvcController {

//    @PostMapping("/mock/test")
//    public Resp mockTest(@RequestBody Person person){
//        Person person1 = new Person();
//        BeanUtils.copyProperties(person,person1);
//        return Resp.success(person1);
//    }
}
