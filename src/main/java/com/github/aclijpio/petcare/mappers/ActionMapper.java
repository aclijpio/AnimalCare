package com.github.aclijpio.petcare.mappers;

import com.github.aclijpio.petcare.dtos.ActionDto;
import com.github.aclijpio.petcare.entities.Action;
import com.github.aclijpio.petcare.mappers.config.BaseMapper;
import com.github.aclijpio.petcare.mappers.config.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface ActionMapper extends BaseMapper<Action, ActionDto> {
}
