package com.github.aclijpio.animalcare.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActionResponse {

    private LocalTime time;
    private String name;
    private long minutesLeft;
}
