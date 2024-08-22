package com.github.aclijpio.animalcare.controllers;

import com.github.aclijpio.animalcare.dtos.request.ActionRequest;
import com.github.aclijpio.animalcare.dtos.request.AnimalRequest;
import com.github.aclijpio.animalcare.dtos.response.ActionResponse;
import com.github.aclijpio.animalcare.dtos.response.RecommendationResponse;
import com.github.aclijpio.animalcare.services.ScheduleActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("schedules/actions")
public class ScheduleActionController {

    private final ScheduleActionService service;

    @GetMapping
    ResponseEntity<List<ActionResponse>> getAllActions() {
        return service.getAllActions();
    }

    @GetMapping("current")
    ResponseEntity<ActionResponse> getCurrentAction(){
        return service.getCurrentAction();
    }

    @GetMapping("prev")
    ResponseEntity<ActionResponse> getPreviousAction(){
        return service.getPreviousAction();
    }

    @GetMapping("next")
    ResponseEntity<ActionResponse> getNextAction(){
        return service.getNextAction();
    }

    @GetMapping("name")
    ResponseEntity<ActionResponse> getActionByName(@RequestParam(required = true) String action){
        return service.findActionByName(action);
    }

    @DeleteMapping("delete")
    ResponseEntity<Void> deleteAction(@RequestParam(required = true) String action){
        service.deleteAction(action);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("add")
    ResponseEntity<ActionResponse> addAction(@RequestBody ActionRequest action){
        return service.createAction(action);
    }

    @PatchMapping("replace")
    ResponseEntity<Void> replaceAction(@RequestBody ActionRequest action){
        service.updateAction(action);
        return ResponseEntity.ok().build();
    }
    @PostMapping("recom")
    public RecommendationResponse getRecommendation(@RequestBody AnimalRequest animalRequest) {
        return service.getRecommendation(animalRequest);
    }
}
