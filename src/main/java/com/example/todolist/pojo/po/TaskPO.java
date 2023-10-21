package com.example.todolist.pojo.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskPO {
    private String content;
    private LocalDateTime createTime;
}
