package com.tcj.blogs.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tcj.blogs.utils.ReadApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.TestOnly;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class JWTUtils {

    private final ReadApplicationProperties readConfig;

    public String getToken(String userName) {

        JWTCreator.Builder builder = JWT.create();
        Map<String, Object> header = new HashMap<>();
        header.put("algo", "HMAC256");
        header.put("typ", "JWT");

        Date issuedTime = new Date();
        Date expiredTime = new Date(issuedTime.getTime() + readConfig.getJwtTimeout() * 1000);

        builder.withHeader(header);
        builder.withClaim("UserName", userName);
        String token = builder.withIssuedAt(issuedTime).withExpiresAt(expiredTime).sign(Algorithm.HMAC256(readConfig.getJwtSecret()));
        return token;
    }

    public boolean verifyToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(readConfig.getJwtSecret())).build();
        DecodedJWT jwt = jwtVerifier.verify(token);
        return true;
    }

//    public String refreshToken(String token) {
//        // 1. 解析token payload, 获取userId
//        // 2. 获取用户密码
//        // 3. 框架验证token
//        // 4. 程序验证token
//
//        String refreshToken = "";
//
//        DecodedJWT jwtToken = JWT.decode(token);
//        Date currentTime = new Date();
//        Date expiredTime = jwtToken.getExpiresAt();
//        Date issuedTime = jwtToken.getIssuedAt();
//        String userName = jwtToken.getClaim("UserName").asString();
//
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(readConfig.getJwtSecret())).build();
//
//        try { // 未过期刷新
//            jwtToken = verifier.verify(token);
//
//            log.info("not expired refresh");
//            boolean isValid = refreshTokenVerify(expiredTime, issuedTime, currentTime);
//            if (!isValid) {
//                log.info("can't refresh, not meet condition");
//                // 刷新失败, 返回失败结果
//                return refreshToken;
//            }
//
//            // 生成新token
//            refreshToken = getToken(userName);
//        } catch (TokenExpiredException e) { // 过期刷新
//            log.info("expired refresh");
//            if(currentTime.getTime() - expiredTime.getTime() > readConfig.getTimeoutAllowRefreshTime() * 1000) {
//                log.info("can't refresh, not meet condition, timeout too long");
//                return refreshToken;
//            }
//            // 生成新token
//            refreshToken = getToken(userName);
//        }
//
//        return refreshToken;
//    }

//    private boolean refreshTokenVerify(Date expiredTime, Date issuedTime, Date currentTime) {
//
//        long deltaTime = expiredTime.getTime() - currentTime.getTime();
//        if (deltaTime > readConfig.getNotAllowRefreshTime() * 1000) {
//            log.info("not allow refresh token, because expireTime greater allow refresh time");
//            log.info("expireTime: {}， currentTime: {}, delta time: {}", expiredTime, currentTime, deltaTime);
//            return false;
//        }
//
//        deltaTime = currentTime.getTime() - issuedTime.getTime();
//        if (deltaTime < readConfig.getLeastRefreshInterval() * 1000) {
//            log.info("refresh interval time too short, not allow refresh token");
//            log.info("issuedTime: {}, currentTime: {}, delta time: {}", issuedTime, currentTime, deltaTime);
//            return false;
//        }
//
//        return true;
//    }
}
