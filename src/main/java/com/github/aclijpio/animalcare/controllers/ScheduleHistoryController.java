package com.github.aclijpio.animalcare.controllers;


import com.github.aclijpio.animalcare.dtos.ScheduleHistoryDto;
import com.github.aclijpio.animalcare.services.ScheduleHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/histories")
public class ScheduleHistoryController {

    private final ScheduleHistoryService service;

    @GetMapping
    List<ScheduleHistoryDto> getAllScheduleHistory(@RequestParam(required = false) LocalDate date) {
        if (date == null)
            return service.getAllScheduleHistory();
        return service.getAllScheduleHistoryByDate(date);
    }
    @GetMapping("{id}")
    ScheduleHistoryDto getScheduleHistoryById(@PathVariable("id") Long id) {
        return service.getScheduleHistoryById(id);
    }




}
