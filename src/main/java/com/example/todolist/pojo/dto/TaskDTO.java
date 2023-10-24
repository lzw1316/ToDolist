package com.example.todolist.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Integer id;
    private String content;
    private LocalDateTime createTime;
    private String label;
    private Integer serialNumber;
}
