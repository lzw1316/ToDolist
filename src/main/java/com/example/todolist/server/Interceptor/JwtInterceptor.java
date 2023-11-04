package com.example.todolist.server.Interceptor;

import com.example.todolist.common.constant.JwtClaimsConstant;
import com.example.todolist.common.properties.JwtProperties;
import com.example.todolist.common.utils.BaseUtils;
import com.example.todolist.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {


    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getToDoTokenName());

        //2、校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtils.praseJwt(jwtProperties.getToDoSecretKey(), token);
            String account = String.valueOf(claims.get(JwtClaimsConstant.User_Account));
            BaseUtils.setCurrentAccount(account);
            log.info("当前登录的账号：{}",account);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            //4、不通过，响应401
            response.setStatus(401);
            return false;
        }
    }
    }

