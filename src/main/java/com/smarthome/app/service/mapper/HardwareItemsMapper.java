package com.smarthome.app.service.mapper;

import com.smarthome.app.domain.HardwareItems;
import com.smarthome.app.domain.RequirementItem;
import com.smarthome.app.service.dto.HardwareItemsDTO;
import com.smarthome.app.service.dto.RequirementItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HardwareItems} and its DTO {@link HardwareItemsDTO}.
 */
@Mapper(componentModel = "spring")
public interface HardwareItemsMapper extends EntityMapper<HardwareItemsDTO, HardwareItems> {
    @Mapping(target = "item", source = "item", qualifiedByName = "requirementItemId")
    HardwareItemsDTO toDto(HardwareItems s);

    @Named("requirementItemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequirementItemDTO toDtoRequirementItemId(RequirementItem requirementItem);
}
