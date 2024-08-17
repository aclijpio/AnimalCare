package com.github.aclijpio.animalcare.services;

import com.github.aclijpio.animalcare.dtos.request.ActionRequest;
import com.github.aclijpio.animalcare.dtos.response.ActionResponse;

import java.util.List;

public interface ScheduleActionService {
    void createAction(ActionRequest action);
    List<ActionResponse> getAllActions();
    ActionResponse findActionByName(String name);
    ActionResponse getCurrentAction();
    ActionResponse getNextAction();
    ActionResponse getPreviousAction();
    void updateAction(ActionRequest action);
    void deleteAction(String name);
}
