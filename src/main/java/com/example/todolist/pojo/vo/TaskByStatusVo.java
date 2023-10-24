package com.example.todolist.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class TaskByStatusVo {
    private Integer id;
    private String content;
    private LocalDateTime createTime;
    private Integer status;
    private String label;
}
