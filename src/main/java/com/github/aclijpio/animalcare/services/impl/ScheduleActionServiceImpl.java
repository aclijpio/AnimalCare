package com.github.aclijpio.animalcare.services.impl;


import com.github.aclijpio.animalcare.configs.ExecutionTriggerListener;
import com.github.aclijpio.animalcare.dtos.request.ActionRequest;
import com.github.aclijpio.animalcare.dtos.request.AnimalRequest;
import com.github.aclijpio.animalcare.dtos.response.ActionResponse;
import com.github.aclijpio.animalcare.dtos.response.AnimalResponse;
import com.github.aclijpio.animalcare.dtos.response.RecommendationActionResponse;
import com.github.aclijpio.animalcare.dtos.response.RecommendationResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ScheduleActionServiceImpl implements ScheduleActionService {

    private final Scheduler scheduler; // Планировщик Quartz для управления задачами
    private final JobDetail jobDetail; // Информация о задаче, связанной с триггерами
    private final ActionMapper actionMapper;
    private final ExecutionTriggerListener triggerListener; // Слушатель для отслеживания текущих и предыдущих триггеров
    private final ScheduleRepository scheduleRepository;
    private final ScheduleHistoryRepository historyRepository;

    /**
     * Создает новый триггер, и сохраняет его в график.
     * @param action, который хранить информацию для создания триггера.
     * @return созданный action
     */
    @Override
    @Transactional
    public ResponseEntity<ActionResponse> createAction(ActionRequest action) {
        try {
            // Поиск последнего созданного расписания
            Optional<Schedule> optionalSchedule = scheduleRepository.findTopByOrderByCreatedDateDesc();

            Schedule schedule;

            if (optionalSchedule.isPresent()) {
                Schedule currentSchedule = optionalSchedule.get();
                // Проверка, существует ли запись в истории для текущего расписания
                boolean existsSchedule = historyRepository.existsByScheduleId(currentSchedule.getId());
                if (existsSchedule) {
                    // Копирование текущего расписания, если оно уже есть в истории
                    schedule = Schedule.copyOf(currentSchedule);
                    schedule.setCreatedDateNow();
                    schedule.setId(null);
                } else
                    schedule = currentSchedule;
            } else {
                // Создание нового расписания, если ничего не найдено
                schedule = new Schedule();
                schedule.setCreatedDateNow();
            }
            schedule.addTriggerName(action.name());
            scheduleRepository.save(schedule);
            scheduler.scheduleJob(newTrigger(action));

            return ResponseEntity.created(this.buildActionUri(action.name())).build();

        } catch (ObjectAlreadyExistsException e) {
            throw new ActionCreationException("Failed to create action: " + action.name() + "because it already exists.", e);
        } catch (SchedulerException e) {
            throw new ActionCreationException("Failed to create action", e);
        }
    }

    /**
     * Используя jobKey, получаем список триггеров и преобразуем его в action
     * @return все действия
     */
    @Override
    public ResponseEntity<List<ActionResponse>> getAllActions() {
        try {
            List<? extends Trigger> triggerList = scheduler.getTriggersOfJob(jobDetail.getKey());

            return ResponseEntity.ok(triggerList.stream().map(actionMapper::toDto).toList());
        } catch (SchedulerException e) {
            throw new ActionCreationException("Failed to find actions", e);
        }
    }

    /**
     * Посредством методов класса Trigger получаем действие, иначе бросаем исключение.
     * @param name название триггера
     * @return действие по названию
     */
    @Override
    public ResponseEntity<ActionResponse> findActionByName(String name) {
        try {
            return ResponseEntity.ok(actionMapper.toDto(findTriggerByName(name)));
        } catch (SchedulerException | TriggerNotFoundException e ) {
            throw new ActionNotFoundException("Action not found with name: " + name, e);
        }
    }

    /**
     * С помощью TriggerListener, который мы добавили в Scheduler,
     * получаем текущее действие, иначе бросаем исключение для дальнейшего преобразования.
     * @return текущее действие.
     */
    @Override
    public ResponseEntity<ActionResponse> getCurrentAction() {
        Trigger trigger = triggerListener.getCurrentTrigger();

        if (trigger == null)
            throw new ActionNotFoundException("Current action not found");

        return ResponseEntity.ok(actionMapper.toDto(trigger));
    }

    /**
     * Получаем следующее действие, иначе бросаем исключение для дальнейшего преобразования.
     * @return следующие действие.
     */
    @Override
    public ResponseEntity<ActionResponse> getNextAction() {
        try {
            // Получение всех триггеров, связанных с заданием
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());

            // Поиск следующего запланированного триггера
            ActionResponse nextAction = triggers.stream()
                    .filter(trigger -> trigger.getNextFireTime() != null && trigger.getNextFireTime().after(Date.from(Instant.now())))
                    .min(Comparator.comparing(Trigger::getNextFireTime))
                    .map(actionMapper::toDto)
                    .orElse(null);

            if (nextAction == null)
                throw new ActionNotFoundException("No upcoming actions found or all triggers have already fired");

            return ResponseEntity.ok(nextAction);

        } catch (SchedulerException | NullPointerException e) {
            throw new ActionNotFoundException("Failed to get next action", e);
        }
    }

    /**
     * С помощью TriggerListener, который мы добавили в Scheduler,
     * получаем предыдущее действие, иначе бросаем исключение для дальнейшего преобразования.
     * @return предыдущее действие.
     */
    @Override
    public ResponseEntity<ActionResponse> getPreviousAction() {
        Trigger trigger = triggerListener.getPrevTrigger();

        if (trigger == null)
            throw new ActionNotFoundException("No previous action found");
        return ResponseEntity.ok(actionMapper.toDto(trigger));
    }

    /**
     * Обновляем действие по его имени и полей запроса.
     * @param action, действие, которое обновляется.
     */
    @Override
    @Transactional
    public void updateAction(ActionRequest action) {

        try {
            Trigger currentTrigger = findTriggerByName(action.name());
            Trigger newTrigger =  newTrigger(action);
            scheduler.rescheduleJob(currentTrigger.getKey(), newTrigger);
        } catch (SchedulerException e) {
            throw new ActionCreationException("Failed to find action with name: " + action.name(), e);
        }

    }

    /**
     * По имени удаляем из таблицы триггера и графика действие.
     * @param name, название действия, который мы ходим удалить.
     */
    @Override
    @Transactional
    public void deleteAction(String name) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findTopByOrderByCreatedDateDesc();
        optionalSchedule.ifPresentOrElse(
                (schedule) -> {
                    boolean existsAction = historyRepository.existsByScheduleTriggerName(schedule.getId(), name);
                    Schedule updateSchedule;
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
    @Override
    public RecommendationResponse getRecommendation(AnimalRequest animalRequest){
        AnimalResponse response = AnimalResponse.builder()
                .id(animalRequest.id())
                .breed(animalRequest.breed())
                .name(animalRequest.name())
                .species(animalRequest.species())
                .build();

        try {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());

            List<RecommendationActionResponse> actions = triggers.stream()
                    .map(trigger -> new RecommendationActionResponse(trigger.getKey().getName(), trigger.getNextFireTime())).toList();

            return new RecommendationResponse(response, actions);

        } catch (SchedulerException e) {
            throw new ActionNotFoundException("Failed to get recommendation", e);
        }


    }


    private URI buildActionUri(String actionName){
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/name")
                .queryParam("action", actionName)
                .build()
                .toUri();

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
