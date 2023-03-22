//package com.tcj.blogs.jwt;
//
//import com.tcj.blogs.utils.StatusCode;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@AllArgsConstructor
//public class JWTService {
//
//    private final JWTUtils jwtUtils;
//
//    private final UserManageService userManageService;
//
//    public GetOrRefreshTokenResponse getToken(String userName, String password) {
//        GetOrRefreshTokenResponse response = GetOrRefreshTokenResponse.getInstance();
//        // 1.校验用户信息
//        UserManage userManage = userManageService.findUserByUsernameAndPwd(userName, password);
//        if (userManage == null) {
//            log.info("user: {} not exist", userName);
//            response.setStatusCode(StatusCode.DealFail.getCode());
//            response.setStatusDesc(StatusCode.DealFail.getMsg());
//        }
//
//        String token = jwtUtils.getToken(userName);
//        if (token != null && !token.isEmpty()) {
//            response.setStatusCode(StatusCode.DealSuccess.getCode());
//            response.setStatusDesc(StatusCode.DealSuccess.getMsg());
//            response.setToken(token);
//        } else {
//            response.setStatusCode(StatusCode.DealFail.getCode());
//            response.setStatusDesc(StatusCode.DealFail.getMsg());
//        }
//
//        return response;
//    }
//
//    public WebUICommonResponse refreshToken(String token) {
//        WebUICommonResponse response = WebUICommonResponse.getInstance();
//
//        String refreshToken = jwtUtils.refreshToken(token);
//        if (refreshToken != null && !refreshToken.isEmpty() && !token.equals(refreshToken)) {
//            response.setStatusCode(StatusCode.DealSuccess.getCode());
//            response.setStatusDesc(StatusCode.DealSuccess.getMsg());
//            response.setResult(refreshToken);
//        }
//        else {
//            response.setStatusCode(StatusCode.TOKEN_EXPIRE.getCode());
//            response.setStatusDesc(StatusCode.TOKEN_EXPIRE.getMsg());
//        }
//
//        return response;
//    }
//}
