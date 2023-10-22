package com.example.todolist.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TaskByStatusVo {
    private Integer id;
    private String content;
    private LocalDateTime createTime;
    private Integer status;
}
