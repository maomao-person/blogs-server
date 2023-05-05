package com.tcj.blogs.service;

import com.tcj.blogs.entity.User;
import com.tcj.blogs.entity.login.LoginArguments;
import com.tcj.blogs.entity.login.LoginResponse;

public interface UserService {
    LoginResponse findUser(LoginArguments loginArguments);

    User insertUser(User user);
    void sendAuthCode(LoginArguments loginArguments);
    int verifyCode(String num ,String mail);
}
