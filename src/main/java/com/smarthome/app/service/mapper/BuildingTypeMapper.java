package com.smarthome.app.service.mapper;

import com.smarthome.app.domain.BuildingType;
import com.smarthome.app.service.dto.BuildingTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BuildingType} and its DTO {@link BuildingTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface BuildingTypeMapper extends EntityMapper<BuildingTypeDTO, BuildingType> {}
