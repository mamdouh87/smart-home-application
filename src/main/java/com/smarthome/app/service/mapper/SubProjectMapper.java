package com.smarthome.app.service.mapper;

import com.smarthome.app.domain.Project;
import com.smarthome.app.domain.SubProject;
import com.smarthome.app.service.dto.ProjectDTO;
import com.smarthome.app.service.dto.SubProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubProject} and its DTO {@link SubProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubProjectMapper extends EntityMapper<SubProjectDTO, SubProject> {
    @Mapping(target = "project", source = "project", qualifiedByName = "projectId")
    SubProjectDTO toDto(SubProject s);

    @Named("projectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoProjectId(Project project);
}
