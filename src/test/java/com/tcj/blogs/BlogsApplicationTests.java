package com.tcj.blogs;

import com.tcj.blogs.entity.login.VerificationCode;
import com.tcj.blogs.redis.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@SpringBootTest
class BlogsApplicationTests {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private JavaMailSenderImpl mailSender;

    //    private StringRedisTemplate stringRedisTemplate;
    @Test
    void contextLoads() {
//        VerificationCode verificationCode = new VerificationCode("6666666");
//        redisUtils.set("code", 1233, 1);
    }

    @Test
    void contextLoads1() {
        System.out.println(redisUtils.get("code"));
    }

    @Test
    void contextLoads2() throws Exception{
//简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("2392008736@qq.com");
        simpleMailMessage.setTo("434941849@qq.com");
        simpleMailMessage.setSubject("Happy New Year");
        simpleMailMessage.setText("新年快乐！");
        mailSender.send(simpleMailMessage);

    }

}
