package com.tcj.blogs.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public enum StatusCode {

    DealSuccess("0", "deal success"),

    DealFail("-1", "deal fail"),
    OtherError("000", "other error"),
    FailureCode("102","验证码已失效"),
    FaultCode("103","验证码错误"),
    ;
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsgByCode(String code) {
        for (StatusCode s : StatusCode.values()) {
            if (s.getCode().equals(code)) {
                return s.getMsg();
            }
        }
        log.info("code {} invalid", code);
        return StatusCode.OtherError.getMsg();
    }
}
