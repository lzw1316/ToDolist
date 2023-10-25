package com.example.todolist.pojo.po;

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
public class JwtByContentPo implements Serializable {
    private Integer id;
    private String content;
    private LocalDateTime updateTime;
    private Integer status;
    private String label;
    private String token;
}
