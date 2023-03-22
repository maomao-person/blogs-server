package com.tcj.blogs.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ReadApplicationProperties {

    @Value("${jwt.token.timeout}")
    private Integer jwtTimeout;

    @Value("${jwt.security.secret}")
    private String jwtSecret;
    @Value("${cross.origin.maxAge}")
    private Integer maxAge;
}
