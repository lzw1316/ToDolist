package com.example.todolist.pojo.dto;

import com.example.todolist.pojo.domain.InformationParents;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor

public class InformationDTO extends InformationParents {
    private Integer id;
    private String image;

    @Builder(toBuilder = true)
    public InformationDTO(String userUsername, String sex, Date birthday, String phone, String job, String school, String cityLocation, String homeland, String userEmail, Integer id, String image) {
        super(userUsername, sex, birthday, phone, job, school, cityLocation, homeland, userEmail);
        this.id = id;
        this.image = image;
    }
}

