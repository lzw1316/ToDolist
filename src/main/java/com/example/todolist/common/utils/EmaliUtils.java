package com.example.todolist.common.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class EmaliUtils {

    @Cacheable(value = "EmailCode",key = "#toEmail")
    public  String getCode(String toEmail){
        return null;
    }
}
