package com.example.exceptiontt;

import com.example.exceptiontt.redis.MyRedis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {

    @Autowired
    private MyRedis myRedis;
    @Test
    public void redisTest(){
        myRedis.redis();
    }
}
