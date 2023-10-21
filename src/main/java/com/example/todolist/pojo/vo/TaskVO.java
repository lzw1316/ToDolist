package com.example.todolist.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskVO {
    private String content;
    private LocalDateTime createTime;
}
