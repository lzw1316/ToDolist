package com.example.todolist.common.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class  JwtResult<Object> implements Serializable {
    private Integer code;
    private String token;
    private String msg;
    private Object data;


    public static <Object> JwtResult<Object> success(Object object,String token) {
        JwtResult<Object> result = new JwtResult<Object>();
        result.data = object;
        result.code = 1;
        result.token=token;
        return result;
    }

    public static <Object> JwtResult<Object> error(String msg) {
        JwtResult result = new JwtResult();
        result.msg = msg;
        result.code = 0;
        return result;
    }
}
