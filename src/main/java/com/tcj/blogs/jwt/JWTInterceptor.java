package com.tcj.blogs.jwt;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class JWTInterceptor implements HandlerInterceptor {

    private final JWTUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Optional<String> optToken = Optional.ofNullable(request.getHeader("token"));

        try {
            if (!optToken.isPresent()) {
                log.info("token can't be null or empty");
                return false;
            }
            jwtUtils.verifyToken(optToken.get());
        }
        catch (SignatureVerificationException e) {
            e.printStackTrace();
            log.info("无效token");
            return false;
        }
        catch (TokenExpiredException e) {
            e.printStackTrace();
            log.info("token过期");
            return false;
        }
        catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            log.info("token算法不一致");
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            log.info("无效token");
            return false;
        }
        return true;
    }
}
