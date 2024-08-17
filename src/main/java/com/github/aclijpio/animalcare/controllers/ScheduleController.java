package com.github.aclijpio.animalcare.controllers;


import com.github.aclijpio.animalcare.dtos.ScheduleDto;
import com.github.aclijpio.animalcare.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

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



}
