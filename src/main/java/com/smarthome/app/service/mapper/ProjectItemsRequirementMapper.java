package com.smarthome.app.service.mapper;

import com.smarthome.app.domain.ProjectItemsRequirement;
import com.smarthome.app.domain.RequirementItem;
import com.smarthome.app.domain.SubProject;
import com.smarthome.app.service.dto.ProjectItemsRequirementDTO;
import com.smarthome.app.service.dto.RequirementItemDTO;
import com.smarthome.app.service.dto.SubProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectItemsRequirement} and its DTO {@link ProjectItemsRequirementDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectItemsRequirementMapper extends EntityMapper<ProjectItemsRequirementDTO, ProjectItemsRequirement> {
    @Mapping(target = "requirementItems", source = "requirementItems", qualifiedByName = "requirementItemId")
    @Mapping(target = "subProject", source = "subProject", qualifiedByName = "subProjectId")
    ProjectItemsRequirementDTO toDto(ProjectItemsRequirement s);

    @Named("requirementItemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequirementItemDTO toDtoRequirementItemId(RequirementItem requirementItem);

    @Named("subProjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubProjectDTO toDtoSubProjectId(SubProject subProject);
}
