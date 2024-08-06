package com.github.aclijpio.petcare.dtos;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ActionDto {

    private Long id;
    private LocalTime time;
    private String name;

}
