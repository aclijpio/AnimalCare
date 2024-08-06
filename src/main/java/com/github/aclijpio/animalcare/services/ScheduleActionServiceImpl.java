package com.github.aclijpio.animalcare.services;


import com.github.aclijpio.animalcare.dtos.request.ActionRequest;
import com.github.aclijpio.animalcare.dtos.response.ActionResponse;
import com.github.aclijpio.animalcare.exceptions.ActionCreationException;
import com.github.aclijpio.animalcare.utils.CronUtil;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleActionServiceImpl implements ScheduleActionService {

    private final Scheduler scheduler;
    private final JobDetail jobDetail;

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
    public List<ActionResponse> findAllActions() {
        try {
            List<? extends Trigger> triggerList = scheduler.getTriggersOfJob(jobDetail.getKey());

            return triggerList.stream().map(trigger -> {

                LocalTime nextTime = trigger.getNextFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

                LocalTime currentTime = LocalTime.now();
                long minutesDifference  = Duration.between(currentTime, nextTime).toMinutes();



               return ActionResponse.builder()
                       .name(trigger.getKey().getName())
                       .time(nextTime)
                       .minutesLeft(minutesDifference)
                       .build();
            }).toList();


        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

}
