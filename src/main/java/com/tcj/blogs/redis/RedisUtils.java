package com.tcj.blogs.redis;

import com.tcj.blogs.entity.login.VerificationCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtils {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public void set(String key, String code, int time) {
        redisTemplate.opsForValue().set(key, code, time, TimeUnit.MINUTES);
        if (get(key) == null) {
            log.info("{}-存入redis失败", key);
        } else {
            log.info("{}-存入redis成功", key);
        }
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long getExpirationTime(String key) {
        return redisTemplate.getExpire(key);
    }
}
