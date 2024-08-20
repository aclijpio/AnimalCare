package com.github.aclijpio.animalcare.services.impl;


import com.github.aclijpio.animalcare.configs.ExecutionTriggerListener;
import com.github.aclijpio.animalcare.dtos.request.ActionRequest;
import com.github.aclijpio.animalcare.dtos.response.ActionResponse;
import com.github.aclijpio.animalcare.entities.Schedule;
import com.github.aclijpio.animalcare.exceptions.ScheduleNotFoundException;
import com.github.aclijpio.animalcare.exceptions.TriggerNotFoundException;
import com.github.aclijpio.animalcare.exceptions.action.ActionCreationException;
import com.github.aclijpio.animalcare.exceptions.action.ActionNotFoundException;
import com.github.aclijpio.animalcare.mappers.ActionMapper;
import com.github.aclijpio.animalcare.repositories.ScheduleHistoryRepository;
import com.github.aclijpio.animalcare.repositories.ScheduleRepository;
import com.github.aclijpio.animalcare.services.ScheduleActionService;
import com.github.aclijpio.animalcare.utils.CronUtil;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleActionServiceImpl implements ScheduleActionService {

    private final Scheduler scheduler;
    private final JobDetail jobDetail;
    private final ActionMapper actionMapper;

    private final ExecutionTriggerListener triggerListener;

    private final ScheduleRepository scheduleRepository;
    private final ScheduleHistoryRepository historyRepository;

    @Override
    public void createAction(ActionRequest action) {
        try {
            Optional<Schedule> optionalSchedule = scheduleRepository.findTopByOrderByCreatedDateDesc();

            Schedule schedule;

            if (optionalSchedule.isPresent()) {
                Schedule currentSchedule = optionalSchedule.get();
                boolean existsSchedule = historyRepository.existsByScheduleId(currentSchedule.getId());

                System.out.println(existsSchedule);

                System.out.println(currentSchedule.getId());

                System.out.println(historyRepository.findById(currentSchedule.getId()).orElse(null));

                if (existsSchedule) {
                    schedule = Schedule.copyOf(currentSchedule);
                    schedule.setCreatedDateNow();
                    schedule.setId(null);
                } else
                    schedule = currentSchedule;
            } else {
                schedule = new Schedule();
                schedule.setCreatedDateNow();
            }
            schedule.addTriggerName(action.name());
            scheduleRepository.save(schedule);
            scheduler.scheduleJob(newTrigger(action));

        } catch (ObjectAlreadyExistsException e) {
            throw new ActionCreationException("Failed to create action: " + action.name() + "because it already exists.", e);
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
    public ActionResponse findActionByName(String name) {
        try {
            return actionMapper.toDto(findTriggerByName(name));
        } catch (SchedulerException | TriggerNotFoundException e ) {
            throw new ActionNotFoundException("Action not found with name: " + name, e);
        }
    }

    @Override
    public ActionResponse getCurrentAction() {
        Trigger trigger = triggerListener.getCurrentTrigger();

        if (trigger == null)
            throw new ActionNotFoundException("Current action not found");

        return actionMapper.toDto(trigger);
    }

    @Override
    public ActionResponse getNextAction() {
        try {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());

            ActionResponse nextAction = triggers.stream()
                    .filter(trigger -> trigger.getNextFireTime() != null && trigger.getNextFireTime().after(Date.from(Instant.now())))
                    .min(Comparator.comparing(Trigger::getNextFireTime))
                    .map(actionMapper::toDto)
                    .orElse(null);

            if (nextAction == null)
                throw new ActionNotFoundException("No upcoming actions found or all triggers have already fired");

            return nextAction;

        } catch (SchedulerException | NullPointerException e) {
            throw new ActionNotFoundException("Failed to get next action", e);
        }
    }

    @Override
    public ActionResponse getPreviousAction() {
        Trigger trigger = triggerListener.getPrevTrigger();

        if (trigger == null)
            throw new ActionNotFoundException("No previous action found");
        return actionMapper.toDto(trigger);
    }

    @Override
    public void updateAction(ActionRequest action) {

        try {
            Trigger currentTrigger = findTriggerByName(action.name());
            Trigger newTrigger =  newTrigger(action);
            scheduler.rescheduleJob(currentTrigger.getKey(), newTrigger);
        } catch (SchedulerException e) {
            throw new ActionCreationException("Failed to find action with name: " + action.name(), e);
        }

    }

    @Override
    public void deleteAction(String name) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findTopByOrderByCreatedDateDesc();
        optionalSchedule.ifPresentOrElse(
                (schedule) -> {
                    boolean existsAction = historyRepository.existsByScheduleTriggerName(schedule.getId(), name);

                    Schedule updateSchedule;

                    System.out.println(existsAction);
                    System.out.println(schedule.getId());

                    if (existsAction){
                        updateSchedule = Schedule.copyOf(schedule);
                        updateSchedule.setCreatedDateNow();
                        updateSchedule.setId(null);
                    } else
                        updateSchedule = schedule;

                    scheduleRepository.save(updateSchedule.removeTriggerName(name));
                    try {
                        scheduler.unscheduleJob(findTriggerByName(name).getKey());
                    } catch (SchedulerException e) {
                        throw new TriggerNotFoundException("Trigger not found with name: " + name, e);
                    }

                },
                () -> {
                    throw new ScheduleNotFoundException("Action not found with name " + name);
                }
        );
    }



    private Trigger findTriggerByName(String triggerName) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName);

        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (trigger == null)
            throw new TriggerNotFoundException("Trigger not found with name: " + triggerName);

        return trigger;
    }

    private Trigger newTrigger(ActionRequest action) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(action.name())
                .withSchedule(CronScheduleBuilder.cronSchedule(CronUtil.UTIL.convertLocalTimeToCron(action.time())))
                .withDescription(action.status())
                .build();
    }

}
