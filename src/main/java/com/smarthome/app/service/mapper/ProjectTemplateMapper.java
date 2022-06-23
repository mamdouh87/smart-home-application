package com.smarthome.app.service.mapper;

import com.smarthome.app.domain.ProjectTemplate;
import com.smarthome.app.service.dto.ProjectTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectTemplate} and its DTO {@link ProjectTemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectTemplateMapper extends EntityMapper<ProjectTemplateDTO, ProjectTemplate> {}
