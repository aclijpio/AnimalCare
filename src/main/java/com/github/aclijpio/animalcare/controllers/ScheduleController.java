package com.github.aclijpio.animalcare.controllers;


import com.github.aclijpio.animalcare.dtos.ScheduleDto;
import com.github.aclijpio.animalcare.dtos.request.AdoptionRequest;
import com.github.aclijpio.animalcare.dtos.request.AnimalRequest;
import com.github.aclijpio.animalcare.dtos.response.AnimalResponse;
import com.github.aclijpio.animalcare.dtos.response.RecommendationResponse;
import com.github.aclijpio.animalcare.services.ScheduleService;
import com.github.aclijpio.animalcare.services.TestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;
    private final TestClient client;

    @GetMapping
    List<ScheduleDto> getAllSchedules(){
        return service.getAllSchedules();
    }

    @GetMapping("{id}")
    ScheduleDto getScheduleById(@PathVariable("id") Long id){
        return service.getScheduleById(id);
    }

    @GetMapping("current")
    ScheduleDto getCurrentSchedule(){
        return service.getCurrentSchedule();
    }

    @GetMapping("test")
    List<AdoptionRequest> getTest(){
        return client.getAdoptions();
    }

    @PostMapping("recom")
    public RecommendationResponse getRecommendation(@RequestBody AnimalRequest animalRequest){
        AnimalResponse response = AnimalResponse.builder()
                .id(animalRequest.id())
                .breed(animalRequest.breed())
                .name(animalRequest.name())
                .species(animalRequest.species())
                .build();
        ScheduleDto scheduleDto = service.getCurrentSchedule();

        return new RecommendationResponse(response, scheduleDto);
    }

}
