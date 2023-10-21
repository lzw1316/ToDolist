package com.example.todolist.common.result;

import lombok.Data;

@Data
public class Result<Object> {
    private Integer code; //编码：1成功，0和其它数字为失败
    private String msg; //错误信息
    private Object data; //数据

    public static <Object> Result<Object> success() {
        Result<Object> result = new Result<Object>();
        result.code = 1;
        return result;
    }

    public static <Object> Result<Object> success(Object object) {
        Result<Object> result = new Result<Object>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <Object> Result<Object> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }
}
