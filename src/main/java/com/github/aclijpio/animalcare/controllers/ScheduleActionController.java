package com.github.aclijpio.animalcare.controllers;

import com.github.aclijpio.animalcare.dtos.request.ActionRequest;
import com.github.aclijpio.animalcare.dtos.response.ActionResponse;
import com.github.aclijpio.animalcare.services.ScheduleActionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/actions")
public class ScheduleActionController {

    private final ScheduleActionServiceImpl service;

    @GetMapping
    List<ActionResponse> getAllActions() {
        return service.findAllActions();
    }

    @GetMapping("current")
    void getCurrentActionSchedule(){

    }

    @GetMapping("prev")
    void getPreviousActionSchedule(){

    }

    @GetMapping("next")
    void getNextActionSchedule(){

    }

    @GetMapping("{id}")
    void getActionById(@PathVariable Long id){

    }

    @DeleteMapping("{id}/delete")
    void deleteAction(@PathVariable("id") String id){

    }

    @PostMapping()
    void addAction(@RequestBody ActionRequest action){

        System.out.println(action);

        service.createAction(action);
    }

    @PutMapping()
    void replaceAction(@RequestBody ActionRequest action){

    }

}
