package com.smarthome.app.service.mapper;

import com.smarthome.app.domain.ProjectTemplate;
import com.smarthome.app.domain.SubProjectTemplate;
import com.smarthome.app.service.dto.ProjectTemplateDTO;
import com.smarthome.app.service.dto.SubProjectTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubProjectTemplate} and its DTO {@link SubProjectTemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubProjectTemplateMapper extends EntityMapper<SubProjectTemplateDTO, SubProjectTemplate> {
    @Mapping(target = "projectTemplate", source = "projectTemplate", qualifiedByName = "projectTemplateId")
    SubProjectTemplateDTO toDto(SubProjectTemplate s);

    @Named("projectTemplateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectTemplateDTO toDtoProjectTemplateId(ProjectTemplate projectTemplate);
}
