package com.github.aclijpio.animalcare.mappers;


import com.github.aclijpio.animalcare.dtos.response.ActionResponse;
import com.github.aclijpio.animalcare.mappers.config.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.quartz.Trigger;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Mapper(config = MapStructConfig.class)
public interface ActionMapper {
    @Mapping(source = "startTime", target = "time", qualifiedByName = "convertToTime")
    @Mapping(source = "key.name", target = "name")
    @Mapping(source = "nextFireTime", target = "minutesLeft", qualifiedByName = "calculateMinutesLeft")
    ActionResponse toDto(Trigger entity);

    List<ActionResponse> toDtoList(List<Trigger> entities);

    @Named("convertToTime")
    default LocalTime convertToTime(Date startTime) {
        return startTime != null ? startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime() : null;
    }

    @Named("calculateMinutesLeft")
    default long calculateMinutesLeft(Date nextFireTime) {
        if (nextFireTime != null) {
            long diff = nextFireTime.getTime() - System.currentTimeMillis();
            return TimeUnit.MILLISECONDS.toMinutes(diff);
        }
        return 0;
    }

}
