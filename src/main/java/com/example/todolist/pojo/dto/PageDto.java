package com.example.todolist.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    private String label;
    private Integer index;
    private Integer pages;
    private String account;
}
