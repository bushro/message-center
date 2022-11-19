//package com.bushro.oauth2.server.com.bushro.common.redis.config;
//
//import cn.hutool.core.com.bushro.common.redis.util.StrUtil;
//import com.bushro.common.core.com.bushro.common.redis.util.R;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
///**
// * 认证失败处理
// */
//@Component
//public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//    @Resource
//    private ObjectMapper objectMapper;
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response,
//                         AuthenticationException authException) throws IOException {
//        // 返回 JSON
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        // 状态码 401
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        // 写出
//        PrintWriter out = response.getWriter();
//        String errorMessage = authException.getMessage();
//        if (StrUtil.isEmpty(errorMessage)) {
//            errorMessage = "登录失效!";
//        }
//        out.write(objectMapper.writeValueAsString(R.failed(errorMessage)));
//        out.flush();
//        out.close();
//    }
//
//}
