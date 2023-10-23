package com.example.todolist.pojo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskByStatusDto {
    private Integer id;
    private String content;
    private LocalDateTime createTime;
    private Integer status;
}
