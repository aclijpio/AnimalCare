package com.github.aclijpio.animalcare.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleHistoryDto {

    private Long id;
    private LocalDate date;
    private ScheduleDto schedule;

}
