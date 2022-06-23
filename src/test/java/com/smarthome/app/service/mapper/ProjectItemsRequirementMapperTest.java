package com.smarthome.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectItemsRequirementMapperTest {

    private ProjectItemsRequirementMapper projectItemsRequirementMapper;

    @BeforeEach
    public void setUp() {
        projectItemsRequirementMapper = new ProjectItemsRequirementMapperImpl();
    }
}
