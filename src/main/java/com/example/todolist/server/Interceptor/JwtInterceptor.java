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
        //判断当前请求是否是跨域
        log.info("请求方式为：{}",request.getMethod());
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())){
            return true;
        }
        //请求的地址
        log.info("请求的地址：{}",request.getRequestURL());
        //获取token
        String token = request.getHeader("token");
        //校验令牌
        Claims claims = JwtUtils.praseJwt(jwtProperties.getToDoSecretKey(), token);
        //将令牌中的账号存储到线程中
        String account = String.valueOf(claims.get(JwtClaimsConstant.User_Account));
        BaseUtils.setCurrentAccount(account);
        if (token != null) {
            System.out.println("拦截器中显示当前登录账号为：" + account);
        }
        //通过，放行
        return true;
    }
}

