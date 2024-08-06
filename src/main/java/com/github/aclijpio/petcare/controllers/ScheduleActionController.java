package com.github.aclijpio.petcare.controllers;

import com.github.aclijpio.petcare.dtos.ActionDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shedules/actions")
public class ScheduleActionController {

    @GetMapping("current")
    void getCurrentActionSchedule(){

    }

    @GetMapping("prev")
    void getPreviousActionSchedule(){

    }

    @GetMapping("next")
    void getNextActionSchedule(){

    }

    @DeleteMapping("{id}/delete")
    void deleteAction(@PathVariable("id") String id){

    }

    @PostMapping()
    void addAction(@RequestBody ActionDto action){

    }

    @PutMapping()
    void replaceAction(@RequestBody ActionDto action){

    }

}
