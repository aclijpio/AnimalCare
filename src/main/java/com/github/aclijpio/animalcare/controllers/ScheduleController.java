package com.github.aclijpio.animalcare.controllers;


import com.github.aclijpio.animalcare.dtos.ScheduleDto;
import com.github.aclijpio.animalcare.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @GetMapping
    ResponseEntity<List<ScheduleDto>> getAllSchedules(){
        return service.getAllSchedules();
    }

    @GetMapping("{id}")
    ResponseEntity<ScheduleDto> getScheduleById(@PathVariable("id") Long id){
        return service.getScheduleById(id);
    }

    @GetMapping("current")
    ResponseEntity<ScheduleDto> getCurrentSchedule(){
        return service.getCurrentSchedule();
    }



}
