package com.github.aclijpio.animalcare.controllers;


import com.github.aclijpio.animalcare.dtos.ScheduleHistoryDto;
import com.github.aclijpio.animalcare.services.ScheduleHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("histories")
public class ScheduleHistoryController {

    private final ScheduleHistoryService service;

    @GetMapping
    ResponseEntity<List<ScheduleHistoryDto>> getAllScheduleHistory(@RequestParam(required = false) LocalDate date) {
        if (date == null)
            return ResponseEntity.ok(service.getAllScheduleHistory());
        return ResponseEntity.ok(service.getAllScheduleHistoryByDate(date));
    }
    @GetMapping("{id}")
    ResponseEntity<ScheduleHistoryDto> getScheduleHistoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getScheduleHistoryById(id));
    }
    @PostMapping("commit")
    void commitScheduleHistory() {
        service.commitScheduleHistory();
    }

}
