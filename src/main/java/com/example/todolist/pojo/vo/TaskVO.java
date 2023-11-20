package com.example.todolist.pojo.vo;

import com.example.todolist.pojo.domain.TaskParents;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskVO extends TaskParents implements Serializable {

    @Builder(toBuilder = true)
    public TaskVO(Integer id, String content, LocalDateTime updateTime, Integer serialNumber, Integer status) {
        super(id, content, updateTime, serialNumber, status);
    }


}
