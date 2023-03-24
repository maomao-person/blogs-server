package com.tcj.blogs;

import com.tcj.blogs.entity.login.VerificationCode;
import com.tcj.blogs.redis.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class BlogsApplicationTests {
    @Autowired
    private RedisUtils redisUtils;
//    private StringRedisTemplate stringRedisTemplate;
    @Test
    void contextLoads() {
        VerificationCode verificationCode=new VerificationCode("6666666");
        redisUtils.set("code",verificationCode,1);
    }
    @Test
    void contextLoads1() {
        System.out.println(redisUtils.getExpirationTime("code"));
    }

}
