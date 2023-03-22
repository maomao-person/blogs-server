package com.tcj.blogs.common;

import lombok.Data;

import static com.tcj.blogs.utils.StatusCode.DealFail;


@Data
public class WebUICommonResponse {
    private String statusCode = DealFail.getCode();
    private String statusDesc = DealFail.getMsg();
    private Object result;

    public static WebUICommonResponse getInstance() { return new WebUICommonResponse(); }
}
