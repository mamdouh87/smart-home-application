package com.smarthome.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubProjectAttrTemplateMapperTest {

    private SubProjectAttrTemplateMapper subProjectAttrTemplateMapper;

    @BeforeEach
    public void setUp() {
        subProjectAttrTemplateMapper = new SubProjectAttrTemplateMapperImpl();
    }
}
