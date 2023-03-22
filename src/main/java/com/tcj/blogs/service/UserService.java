package com.tcj.blogs.service;

import com.tcj.blogs.entity.User;
public interface UserService {
    User findUser(User user);

    User insertUser(User user);
}
