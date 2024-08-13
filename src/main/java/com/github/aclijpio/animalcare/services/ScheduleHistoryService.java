package com.github.aclijpio.animalcare.services;

import com.github.aclijpio.animalcare.dtos.ScheduleHistoryDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleHistoryService {
    List<ScheduleHistoryDto> getAllScheduleHistory();
    ScheduleHistoryDto getScheduleHistoryById(Long id);
    List<ScheduleHistoryDto> getAllScheduleHistoryByDate(LocalDate date);
}
