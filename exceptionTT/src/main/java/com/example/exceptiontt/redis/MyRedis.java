package com.example.exceptiontt.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRedis {
    @Autowired
    private StringRedisTemplate stringTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("/redis")
    public  void redis(){
        String key = "周日";
        String val = "假期";
        String obj = new String("对象");
        String objval = "对象值";
        redisTemplate.opsForValue().set(obj,objval);
        redisTemplate.opsForValue().set(key,val);

        System.out.println(redisTemplate.opsForValue().get(obj));
        System.out.println(redisTemplate.opsForValue().get(key));
        System.out.println("hello");

        stringTemplate.opsForValue().set(obj,objval);
        stringTemplate.opsForValue().set(key,val);

        System.out.println(stringTemplate.opsForValue().get(obj));
        System.out.println(stringTemplate.opsForValue().get(key));
    }
}
