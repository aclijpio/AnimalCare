package com.github.aclijpio.animalcare.dtos;

import java.time.LocalDateTime;
import java.util.List;


public record ScheduleDto (Long id, LocalDateTime createdDate, List<String> triggerName) {

}
