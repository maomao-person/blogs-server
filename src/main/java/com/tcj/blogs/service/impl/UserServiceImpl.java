package com.tcj.blogs.service.impl;

import com.tcj.blogs.entity.User;
import com.tcj.blogs.entity.login.LoginArguments;
import com.tcj.blogs.entity.login.LoginResponse;
import com.tcj.blogs.jwt.JWTUtils;
import com.tcj.blogs.mapper.UserMapper;
import com.tcj.blogs.redis.RedisUtils;
import com.tcj.blogs.service.MailService;
import com.tcj.blogs.service.UserService;
import com.tcj.blogs.utils.ReadApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private JWTUtils jwt;
    private MailService mailService;
    private RedisUtils redisUtils;
    private ReadApplicationProperties configParam;

    @Override
    public LoginResponse findUser(LoginArguments loginArguments) {
        LoginResponse loginResponse = LoginResponse.getInstance();
        User user = userMapper.findUser(loginArguments);
        log.info("findUser:{}", user);
        loginResponse.setToken(jwt.getToken(user.getUserName()));
        loginResponse.setUserId(user.getUserId());
        loginResponse.setUserName(user.getUserName());
        loginResponse.setPassWord(user.getPassWord());
        loginResponse.setMailBox(user.getMailBox());

        return loginResponse;
    }

    @Override
    public User insertUser(User user) {
        return null;
    }

    /**
     * 发送验证码
     *
     * @param loginArguments
     */
    public void sendAuthCode(LoginArguments loginArguments) {
        log.info("loginArguments:{}", loginArguments);
        String title = "猫猫验证码";
        String code = generateSixDigits();
        String content = "您的验证码为：" + code + "(15分钟内有效)请勿将验证码告诉他人哦！";
        try {
            redisUtils.set(loginArguments.getMailBox(), code, configParam.getMailTimeout());
            mailService.sendNormalMail(loginArguments.getMailBox(), title, content);
        } catch (Exception e) {
            log.info("发送失败");
        }


    }

    /**
     * 校验验证码
     *
     * @param num
     * @return
     */
    public int verifyCode(String num, String mail) {
        String code = redisUtils.get(mail);
        if (code.equals("")) {
            log.info("验证码失效:{}", code);
            return 1;
        } else {
            if (code.equals(num)) {
                log.info("正确的验证码:{}", code);
                return 0;//正确的验证码
            } else {
                log.info("错误的验证码:{}", code);
                return 2;//错误的验证码
            }
        }
    }

    public String generateSixDigits() {
        Random random = new Random();
        String num = String.valueOf(random.nextInt(900000) + 100000); //生成100000到999999之间的随机数
        log.info("随机生成的6位数为：{}", num);
        return num;
    }
}
