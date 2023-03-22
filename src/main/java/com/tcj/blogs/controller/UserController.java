package com.tcj.blogs.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tcj.blogs.common.WebUICommonResponse;
import com.tcj.blogs.entity.User;
import com.tcj.blogs.service.UserService;
import com.tcj.blogs.utils.StatusCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Component
@RestController
@AllArgsConstructor
@RequestMapping(value = "/user", produces = {"application/json"})
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public WebUICommonResponse login(@RequestBody User user) {
        WebUICommonResponse response = WebUICommonResponse.getInstance();
        if (StringUtils.isEmpty(user.getMailBox()) && StringUtils.isEmpty(user.getPassWord())) {
            response.setStatusCode(StatusCode.DealFail.getCode());
            response.setStatusDesc(StatusCode.DealFail.getMsg());
        }
        System.out.println(user);
        if (userService.findUser(user)!=null) {
            response.setStatusCode(StatusCode.DealSuccess.getCode());
            response.setStatusDesc(StatusCode.DealSuccess.getMsg());
            response.setResult(userService.findUser(user));
        }
        return response;
    }
}
