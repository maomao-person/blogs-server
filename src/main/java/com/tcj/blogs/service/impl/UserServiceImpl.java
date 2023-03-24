package com.tcj.blogs.service.impl;

import com.tcj.blogs.entity.User;
import com.tcj.blogs.entity.login.LoginArguments;
import com.tcj.blogs.entity.login.LoginResponse;
import com.tcj.blogs.jwt.JWTUtils;
import com.tcj.blogs.mapper.UserMapper;
import com.tcj.blogs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private JWTUtils jwt;

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
}
