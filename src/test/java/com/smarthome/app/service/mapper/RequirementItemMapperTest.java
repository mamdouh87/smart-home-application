package com.smarthome.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequirementItemMapperTest {

    private RequirementItemMapper requirementItemMapper;

    @BeforeEach
    public void setUp() {
        requirementItemMapper = new RequirementItemMapperImpl();
    }
}
