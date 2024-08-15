package com.github.aclijpio.animalcare.dtos.response;

import lombok.Builder;

import java.time.LocalTime;


@Builder
public record ActionResponse  (LocalTime time, String name, long minutesLeft){

}
