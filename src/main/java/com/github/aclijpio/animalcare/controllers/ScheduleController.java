package com.github.aclijpio.animalcare.controllers;


import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/schedules")
public class ScheduleController {

    @GetMapping()
    void getAllSchedules(@RequestParam(required = false) LocalDate date){

    }

    @GetMapping("{id}")
    void getScheduleById(@PathVariable("id") String id){

    }

    @GetMapping("current")
    void getCurrentSchedule(){

    }



}
