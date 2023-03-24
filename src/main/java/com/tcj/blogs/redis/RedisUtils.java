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
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object obj, int time) {
        redisTemplate.opsForValue().set(key, obj, time, TimeUnit.MINUTES);
        if (get(key) == null) {
            log.info("{}-存入redis失败", key);
        } else {
            log.info("{}-存入redis成功", key);
        }
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long getExpirationTime(String key) {
        return redisTemplate.getExpire(key);
    }
}
