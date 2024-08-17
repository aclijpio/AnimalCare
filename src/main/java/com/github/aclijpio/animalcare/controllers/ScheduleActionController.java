package com.github.aclijpio.animalcare.controllers;

import com.github.aclijpio.animalcare.dtos.request.ActionRequest;
import com.github.aclijpio.animalcare.dtos.response.ActionResponse;
import com.github.aclijpio.animalcare.services.ScheduleActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("schedules/actions")
public class ScheduleActionController {

    private final ScheduleActionService service;

    @GetMapping
    List<ActionResponse> getAllActions() {
        return service.getAllActions();
    }

    @GetMapping("current")
    ActionResponse getCurrentActionSchedule(){
        return service.getCurrentAction();
    }

    @GetMapping("prev")
    ActionResponse getPreviousActionSchedule(){
        return service.getPreviousAction();
    }

    @GetMapping("next")
    ActionResponse getNextActionSchedule(){
        return service.getNextAction();
    }

    @GetMapping("name")
    ActionResponse getActionByName(@RequestParam(required = true) String action){
        return service.findActionByName(action);
    }

    @DeleteMapping("delete")
    void deleteAction(@RequestParam(required = true) String action){
        service.deleteAction(action);
    }

    @PostMapping("add")
    void addAction(@RequestBody ActionRequest action){
        service.createAction(action);
    }

    @PatchMapping("replace")
    void replaceAction(@RequestBody ActionRequest action){
        service.updateAction(action);
    }

}
