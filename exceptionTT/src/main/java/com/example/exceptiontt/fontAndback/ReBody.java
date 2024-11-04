package com.example.exceptiontt.fontAndback;

import com.example.exceptiontt.exception.Resp;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReBody {

    @RequestMapping("/param")
    public Resp param(String name, @RequestParam("aGe") Integer age){
        return Resp.success(new User(name,age));
    }

    @RequestMapping("/body")
    public Resp body(@RequestBody User user){
        User user1 = new User(user.getName(), user.getAge());
        return Resp.success(user1);
    }
}
