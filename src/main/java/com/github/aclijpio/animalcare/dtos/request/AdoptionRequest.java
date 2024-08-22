package com.github.aclijpio.animalcare.dtos.request;

import java.time.LocalDate;


public record AdoptionRequest(Long id, OwnerRequest owner, Long petId, Long petInfoId, LocalDate date) {

}
