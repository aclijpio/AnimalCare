package com.github.aclijpio.petcare.mappers;

import com.github.aclijpio.petcare.dtos.ScheduleDto;
import com.github.aclijpio.petcare.entities.Schedule;
import com.github.aclijpio.petcare.mappers.config.BaseMapper;
import com.github.aclijpio.petcare.mappers.config.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface ScheduleMapper extends BaseMapper<Schedule, ScheduleDto> {
}
