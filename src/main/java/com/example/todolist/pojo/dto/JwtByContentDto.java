package com.example.todolist.pojo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class JwtByContentDto {
    private Integer id;
    private String content;
    private LocalDateTime createTime;
    private Integer status;
    private String label;
    private String token;
}
