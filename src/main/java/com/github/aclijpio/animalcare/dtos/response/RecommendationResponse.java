package com.github.aclijpio.animalcare.dtos.response;

import com.github.aclijpio.animalcare.dtos.ScheduleDto;

public record RecommendationResponse(AnimalResponse animal, ScheduleDto schedule){
}
