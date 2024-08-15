package com.github.aclijpio.animalcare.dtos;

import java.time.LocalDate;


public record ScheduleHistoryDto (Long id, LocalDate date, ScheduleDto schedule) {

}
