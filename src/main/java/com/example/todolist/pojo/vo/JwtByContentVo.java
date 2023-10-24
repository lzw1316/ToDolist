package com.example.todolist.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtByContentVo {
    private Integer id;
    private String content;
    private LocalDateTime createTime;
    private Integer status;
    private String label;
    private String token;
}
