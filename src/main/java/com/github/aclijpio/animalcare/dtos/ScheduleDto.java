package com.github.aclijpio.animalcare.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

    private Long id;
    private LocalDateTime createdDate;
    private List<String> triggerName;

}
