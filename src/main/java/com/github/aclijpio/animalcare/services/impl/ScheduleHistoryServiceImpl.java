package com.github.aclijpio.animalcare.services.impl;

import com.github.aclijpio.animalcare.dtos.ScheduleHistoryDto;
import com.github.aclijpio.animalcare.entities.ScheduleHistory;
import com.github.aclijpio.animalcare.exceptions.ScheduleNotFoundException;
import com.github.aclijpio.animalcare.mappers.ScheduleHistoryMapper;
import com.github.aclijpio.animalcare.repositories.ScheduleHistoryRepository;
import com.github.aclijpio.animalcare.services.ScheduleHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleHistoryServiceImpl implements ScheduleHistoryService {

    private final ScheduleHistoryRepository repository;
    private final ScheduleHistoryMapper mapper;

    @Override
    public List<ScheduleHistoryDto> getAllScheduleHistory() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public ScheduleHistoryDto getScheduleHistoryById(Long id) {
        Optional<ScheduleHistory> optionalSchedule =  repository.findById(id);
        ScheduleHistory history = optionalSchedule.orElseThrow(
                () -> new ScheduleNotFoundException("Failed to find schedules.")
        );
        return mapper.toDto(history);
    }

    @Override
    public List<ScheduleHistoryDto> getAllScheduleHistoryByDate(LocalDate date) {
        LocalDate end = LocalDate.now();
        List<ScheduleHistory> histories =  repository.findAllByDateBetween(date, end);
        return mapper.toDtoList(histories);
    }
}
