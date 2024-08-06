package com.github.aclijpio.animalcare.dtos.request;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ActionRequest {

    private LocalTime time;
    private String name;
    private String description;

}
