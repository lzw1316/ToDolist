package com.example.todolist.pojo.po;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class JwtByContentPo {
    private Integer id;
    private String content;
    private LocalDateTime createTime;
    private Integer status;
    private String label;
    private String token;
}
