package com.tcj.blogs.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tcj.blogs.entity.User;
import com.tcj.blogs.mapper.UserMapper;
import com.tcj.blogs.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User findUser(User user) {
        return userMapper.findUser(user);
    }

    @Override
    public User insertUser(User user) {
        return null;
    }
}
