package com.github.aclijpio.animalcare.mappers;

import com.github.aclijpio.animalcare.dtos.ScheduleDto;
import com.github.aclijpio.animalcare.entities.Schedule;
import com.github.aclijpio.animalcare.mappers.config.BaseMapper;
import com.github.aclijpio.animalcare.mappers.config.MapStructConfig;
import org.mapstruct.Mapper;


@Mapper(config = MapStructConfig.class)
public interface ScheduleMapper extends BaseMapper<Schedule, ScheduleDto> {
}
