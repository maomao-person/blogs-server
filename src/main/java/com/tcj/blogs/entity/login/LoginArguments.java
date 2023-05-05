package com.tcj.blogs.entity.login;

import lombok.Data;

@Data
public class LoginArguments {
    private String passWord;
    private String mailBox;
    private String securityCode;
}
