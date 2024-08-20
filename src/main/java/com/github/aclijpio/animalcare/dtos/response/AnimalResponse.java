package com.github.aclijpio.animalcare.dtos.response;

import lombok.Builder;

@Builder
public record AnimalResponse(Long id, String name, String species, String breed){

}
