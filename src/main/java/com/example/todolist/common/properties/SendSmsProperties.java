package com.example.todolist.common.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "ja.phone")
public class SendSmsProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
}
