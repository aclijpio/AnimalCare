package com.github.aclijpio.animalcare.services.impl;

import com.github.aclijpio.animalcare.dtos.ScheduleDto;
import com.github.aclijpio.animalcare.entities.Schedule;
import com.github.aclijpio.animalcare.exceptions.ScheduleNotFoundException;
import com.github.aclijpio.animalcare.mappers.ScheduleMapper;
import com.github.aclijpio.animalcare.repositories.ScheduleRepository;
import com.github.aclijpio.animalcare.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository repository;
    private final ScheduleMapper mapper;

    @Override
    public ResponseEntity<List<ScheduleDto>> getAllSchedules() {
        return ResponseEntity.ok(mapper.toDtoList(repository.findAll()));
    }

    @Override
    public ResponseEntity<ScheduleDto> getCurrentSchedule() {
        Optional<Schedule> optionalSchedule =  repository.findTopByOrderByCreatedDateDesc();
        Schedule schedule = optionalSchedule.orElseThrow(
                () -> new ScheduleNotFoundException("Failed to find schedules.")
        );
        return ResponseEntity.ok(mapper.toDto(schedule));
    }

    @Override
    public ResponseEntity<ScheduleDto> getScheduleById(Long id) {
        Optional<Schedule> optionalSchedule =  repository.findById(id);
        Schedule schedule = optionalSchedule.orElseThrow(
                () -> new ScheduleNotFoundException("Failed to find schedules.")
        );
        return ResponseEntity.ok(mapper.toDto(schedule));
    }
}
