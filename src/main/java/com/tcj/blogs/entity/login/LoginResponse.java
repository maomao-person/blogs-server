package com.tcj.blogs.entity.login;

import com.tcj.blogs.common.WebUICommonResponse;
import lombok.Data;

@Data
public class LoginResponse {
    private Integer userId;
    private String userName;
    private String passWord;
    private String mailBox;
    private String token;
    public static LoginResponse getInstance() { return new LoginResponse(); }

}
