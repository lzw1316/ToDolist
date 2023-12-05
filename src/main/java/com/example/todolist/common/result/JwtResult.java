package com.example.todolist.common.result;

import cn.hutool.http.HttpStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class  JwtResult<Object> implements Serializable {
    private Integer code;
    private String token;
    private String msg;
    private Object data;

    private int status;//响应状态


    public static <Object> JwtResult<Object> success(Object object,String token) {
        JwtResult<Object> result = new JwtResult<Object>();
        result.data = object;
        result.code = 1;
        result.status= HttpStatus.HTTP_OK;
        result.token=token;
        return result;
    }

    public static <Object> JwtResult<Object> error(String msg) {
        JwtResult result = new JwtResult();
        result.msg = msg;
        result.code = 0;
        result.status=HttpStatus.HTTP_UNAUTHORIZED;
        return result;
    }
}
