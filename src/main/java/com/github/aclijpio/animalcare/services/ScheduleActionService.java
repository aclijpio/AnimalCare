package com.github.aclijpio.animalcare.services;

import com.github.aclijpio.animalcare.dtos.request.ActionRequest;
import com.github.aclijpio.animalcare.dtos.request.AnimalRequest;
import com.github.aclijpio.animalcare.dtos.response.ActionResponse;
import com.github.aclijpio.animalcare.dtos.response.RecommendationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ScheduleActionService {
    ResponseEntity<ActionResponse> createAction(ActionRequest action);
    ResponseEntity<List<ActionResponse>> getAllActions();
    ResponseEntity<ActionResponse> findActionByName(String name);
    ResponseEntity<ActionResponse> getCurrentAction();
    ResponseEntity<ActionResponse> getNextAction();
    ResponseEntity<ActionResponse> getPreviousAction();
    void updateAction(ActionRequest action);
    void deleteAction(String name);

    RecommendationResponse getRecommendation(AnimalRequest animalRequest);
}
