package com.smarthome.app.service.mapper;

import com.smarthome.app.domain.BuildingType;
import com.smarthome.app.domain.Project;
import com.smarthome.app.domain.ProjectTemplate;
import com.smarthome.app.service.dto.BuildingTypeDTO;
import com.smarthome.app.service.dto.ProjectDTO;
import com.smarthome.app.service.dto.ProjectTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "projectTemplate", source = "projectTemplate", qualifiedByName = "projectTemplateId")
    @Mapping(target = "buildingType", source = "buildingType", qualifiedByName = "buildingTypeId")
    ProjectDTO toDto(Project s);

    @Named("projectTemplateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectTemplateDTO toDtoProjectTemplateId(ProjectTemplate projectTemplate);

    @Named("buildingTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BuildingTypeDTO toDtoBuildingTypeId(BuildingType buildingType);
}
