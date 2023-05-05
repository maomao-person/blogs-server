package com.tcj.blogs.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tcj.blogs.common.WebUICommonResponse;
import com.tcj.blogs.entity.login.LoginArguments;
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
    public WebUICommonResponse login(@RequestBody LoginArguments loginArguments) {
        WebUICommonResponse response = WebUICommonResponse.getInstance();
        if (StringUtils.isEmpty(loginArguments.getMailBox()) && StringUtils.isEmpty(loginArguments.getPassWord())) {
            response.setStatusCode(StatusCode.DealFail.getCode());
            response.setStatusDesc(StatusCode.DealFail.getMsg());
        }else {
            int num=userService.verifyCode(loginArguments.getSecurityCode(), loginArguments.getMailBox());
            if(num==0){
                if (userService.findUser(loginArguments)!=null) {
                    response.setStatusCode(StatusCode.DealSuccess.getCode());
                    response.setStatusDesc(StatusCode.DealSuccess.getMsg());
                    response.setResult(userService.findUser(loginArguments));
                }
            } else if (num==1) {
                response.setStatusCode(StatusCode.FailureCode.getCode());
                response.setStatusDesc(StatusCode.FailureCode.getMsg());
            }else {
                response.setStatusCode(StatusCode.FaultCode.getCode());
                response.setStatusDesc(StatusCode.FaultCode.getMsg());
            }
        }

        return response;
    }

    @PostMapping("/sendAuthCode")
    public WebUICommonResponse sendAuthCode(@RequestBody LoginArguments loginArguments) {
        WebUICommonResponse response = WebUICommonResponse.getInstance();
        if (!StringUtils.isEmpty(loginArguments.getMailBox())) {
            userService.sendAuthCode(loginArguments);
            response.setStatusCode(StatusCode.DealSuccess.getCode());
            response.setStatusDesc(StatusCode.DealSuccess.getMsg());
        }
        return response;
    }

}
