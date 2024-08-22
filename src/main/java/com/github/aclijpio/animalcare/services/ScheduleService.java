package com.github.aclijpio.animalcare.services;

import com.github.aclijpio.animalcare.dtos.ScheduleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ScheduleService {
    ResponseEntity<List<ScheduleDto>> getAllSchedules();
    ResponseEntity<ScheduleDto> getCurrentSchedule();
    ResponseEntity<ScheduleDto> getScheduleById(Long id);
}
