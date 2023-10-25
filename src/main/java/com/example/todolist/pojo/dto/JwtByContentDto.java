package com.example.todolist.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtByContentDto implements Serializable {
    private Integer id;
    private String content;
    private LocalDateTime updateTime;
    private Integer status;
    private String label;
    private String token;
}
