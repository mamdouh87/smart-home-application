package com.smarthome.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectTemplateMapperTest {

    private ProjectTemplateMapper projectTemplateMapper;

    @BeforeEach
    public void setUp() {
        projectTemplateMapper = new ProjectTemplateMapperImpl();
    }
}
