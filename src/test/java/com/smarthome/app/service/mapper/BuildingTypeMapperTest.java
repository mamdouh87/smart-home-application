package com.smarthome.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuildingTypeMapperTest {

    private BuildingTypeMapper buildingTypeMapper;

    @BeforeEach
    public void setUp() {
        buildingTypeMapper = new BuildingTypeMapperImpl();
    }
}
