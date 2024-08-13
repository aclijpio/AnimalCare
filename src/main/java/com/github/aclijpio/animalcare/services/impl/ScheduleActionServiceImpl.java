package com.github.aclijpio.animalcare.services.impl;


import com.github.aclijpio.animalcare.dtos.request.ActionRequest;
import com.github.aclijpio.animalcare.dtos.response.ActionResponse;
import com.github.aclijpio.animalcare.exceptions.ActionCreationException;
import com.github.aclijpio.animalcare.exceptions.ActionNotFoundException;
import com.github.aclijpio.animalcare.mappers.ActionMapper;
import com.github.aclijpio.animalcare.services.ScheduleActionService;
import com.github.aclijpio.animalcare.utils.CronUtil;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleActionServiceImpl implements ScheduleActionService {

    private final Scheduler scheduler;
    private final JobDetail jobDetail;
    private final ActionMapper actionMapper;

    @Override
    public void createAction(ActionRequest action) {
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(action.getName())
                .withSchedule(CronScheduleBuilder.cronSchedule(CronUtil.UTIL.convertLocalTimeToCron(action.getTime())))
                .withDescription(action.getDescription())
                .build();

        try {
            scheduler.scheduleJob(trigger);
        } catch (ObjectAlreadyExistsException e) {
            throw new ActionCreationException("Failed to create action: " + action.getName() + "because it already exists.", e);
        } catch (SchedulerException e) {
            throw new ActionCreationException("Failed to create action", e);
        }
    }

    @Override
    public List<ActionResponse> getAllActions() {
        try {
            List<? extends Trigger> triggerList = scheduler.getTriggersOfJob(jobDetail.getKey());

            return triggerList.stream().map(actionMapper::toDto).toList();
        } catch (SchedulerException e) {
            throw new ActionCreationException("Failed to find actions", e);
        }
    }

    @Override
    public ActionResponse findActionByName(String actionName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(actionName);
            return actionMapper.toDto(scheduler.getTrigger(triggerKey));
        } catch (SchedulerException e) {
            throw new ActionNotFoundException("Action not found with name: " + actionName);
        }
    }


}
