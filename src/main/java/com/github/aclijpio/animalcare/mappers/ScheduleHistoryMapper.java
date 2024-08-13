package com.github.aclijpio.animalcare.mappers;

import com.github.aclijpio.animalcare.dtos.ScheduleHistoryDto;
import com.github.aclijpio.animalcare.entities.ScheduleHistory;
import com.github.aclijpio.animalcare.mappers.config.BaseMapper;
import com.github.aclijpio.animalcare.mappers.config.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface ScheduleHistoryMapper extends BaseMapper<ScheduleHistory, ScheduleHistoryDto> {

}
