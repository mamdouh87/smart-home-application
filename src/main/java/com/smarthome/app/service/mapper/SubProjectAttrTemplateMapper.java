package com.smarthome.app.service.mapper;

import com.smarthome.app.domain.SubProjectAttrTemplate;
import com.smarthome.app.domain.SubProjectTemplate;
import com.smarthome.app.service.dto.SubProjectAttrTemplateDTO;
import com.smarthome.app.service.dto.SubProjectTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubProjectAttrTemplate} and its DTO {@link SubProjectAttrTemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubProjectAttrTemplateMapper extends EntityMapper<SubProjectAttrTemplateDTO, SubProjectAttrTemplate> {
    @Mapping(target = "subProjectTemplate", source = "subProjectTemplate", qualifiedByName = "subProjectTemplateId")
    SubProjectAttrTemplateDTO toDto(SubProjectAttrTemplate s);

    @Named("subProjectTemplateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubProjectTemplateDTO toDtoSubProjectTemplateId(SubProjectTemplate subProjectTemplate);
}
