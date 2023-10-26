package com.example.todolist.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "ja.jwt")
public class JwtProperties {

    //生成jwt令牌的配置
    private String ToDoSecretKey;
    private long ToDoTtl;
    private String ToDoTokenName;
}
