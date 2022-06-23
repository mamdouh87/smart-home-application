package com.smarthome.app.service.mapper;

import com.smarthome.app.domain.RequirementItem;
import com.smarthome.app.domain.SubProjectTemplate;
import com.smarthome.app.service.dto.RequirementItemDTO;
import com.smarthome.app.service.dto.SubProjectTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequirementItem} and its DTO {@link RequirementItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequirementItemMapper extends EntityMapper<RequirementItemDTO, RequirementItem> {
    @Mapping(target = "subProjectTemplate", source = "subProjectTemplate", qualifiedByName = "subProjectTemplateId")
    RequirementItemDTO toDto(RequirementItem s);

    @Named("subProjectTemplateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubProjectTemplateDTO toDtoSubProjectTemplateId(SubProjectTemplate subProjectTemplate);
}
