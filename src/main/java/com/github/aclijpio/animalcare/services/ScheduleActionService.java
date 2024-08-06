package com.github.aclijpio.animalcare.services;

import com.github.aclijpio.animalcare.dtos.request.ActionRequest;
import com.github.aclijpio.animalcare.dtos.response.ActionResponse;

import java.time.LocalTime;
import java.util.List;

public interface ScheduleActionService {
    void createAction(ActionRequest action);
    List<ActionResponse> findAllActions();
}
