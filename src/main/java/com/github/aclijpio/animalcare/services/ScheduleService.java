package com.github.aclijpio.animalcare.services;

import com.github.aclijpio.animalcare.dtos.ScheduleDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDto> getAllSchedules();
    ScheduleDto getCurrentSchedule();
    ScheduleDto getScheduleById(Long id);
}
