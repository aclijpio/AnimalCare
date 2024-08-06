package com.github.aclijpio.petcare.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ScheduleDto {
    private Long id;
    private LocalDate date;
    private List<ActionDto> actions;
}
