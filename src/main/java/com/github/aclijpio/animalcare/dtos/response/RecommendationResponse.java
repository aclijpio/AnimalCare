package com.github.aclijpio.animalcare.dtos.response;


import java.util.List;

public record RecommendationResponse(AnimalResponse animal, List<RecommendationActionResponse> actions){
}
