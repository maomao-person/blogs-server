package com.tcj.blogs.Interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.tcj.blogs.jwt.JWTUtils;
import com.tcj.blogs.utils.CommonUtil;
import com.tcj.blogs.utils.ReadApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class SpecialInterceptor implements HandlerInterceptor {

    private final JWTUtils jwtUtils;
    private final ReadApplicationProperties readConfig;
    private final CommonUtil commonUtil;
    //重写preHandle方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("SpecialInterceptor");
//        String token = request.getHeader("token");
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(readConfig.getJwtSecret())).build();
//        DecodedJWT jwt = jwtVerifier.verify(token);
//        String user_name = jwt.getClaim("user_name").asString();
//        log.info("user_name: {}", user_name);
//        return "admin".equals(user_name);
        log.info("========== SpecialInterceptor ==========");
        // 不写则接收不到 token
        // 为了处理跨域请求, 如果发送的是 OPTIONS 直接正常返回
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        Optional<String> optToken = Optional.ofNullable(request.getHeader("token"));

        try {
            if (!optToken.isPresent()) {
                log.info("token can't be null or empty");
                return false;
            }
            jwtUtils.verifyToken(optToken.get());


            String host = commonUtil.getIpAddr(request);
            String url  = String.valueOf(request.getRequestURL());
            String token= optToken.get();
            log.info("host: {}, url: {}, token: {}", host, url, token);
//            log.info("username: {}", request.getHeader("userName"));

            // 2.获取token中的user_name和获取resource_name
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(readConfig.getJwtSecret())).build();
            DecodedJWT jwt = JWT.decode(token);
            String user_name = jwt.getClaim("UserName").asString();
            String resource_name = url.substring(url.indexOf("/", 8));
            log.info("username: {}, resource_name: {}", user_name, resource_name);

            // 3.用户为admin则放行
            return "admin".equals(user_name);

        } catch (SignatureVerificationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.warn("无效token");
            return false;
        } catch (TokenExpiredException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.warn("token过期");
            return false;
        } catch (AlgorithmMismatchException e) {
            log.warn("token算法不一致");
            return false;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error("Exception message is: {}, cause id: {}", e.getMessage(), e.getCause());
            return false;
        }
    }
}
