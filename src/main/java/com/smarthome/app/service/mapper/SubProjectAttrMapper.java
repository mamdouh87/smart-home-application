package com.smarthome.app.service.mapper;

import com.smarthome.app.domain.SubProject;
import com.smarthome.app.domain.SubProjectAttr;
import com.smarthome.app.service.dto.SubProjectAttrDTO;
import com.smarthome.app.service.dto.SubProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubProjectAttr} and its DTO {@link SubProjectAttrDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubProjectAttrMapper extends EntityMapper<SubProjectAttrDTO, SubProjectAttr> {
    @Mapping(target = "subProject", source = "subProject", qualifiedByName = "subProjectId")
    SubProjectAttrDTO toDto(SubProjectAttr s);

    @Named("subProjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubProjectDTO toDtoSubProjectId(SubProject subProject);
}
