package com.smarthome.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubProjectTemplateMapperTest {

    private SubProjectTemplateMapper subProjectTemplateMapper;

    @BeforeEach
    public void setUp() {
        subProjectTemplateMapper = new SubProjectTemplateMapperImpl();
    }
}
