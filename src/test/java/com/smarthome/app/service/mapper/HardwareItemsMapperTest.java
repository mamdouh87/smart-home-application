package com.smarthome.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HardwareItemsMapperTest {

    private HardwareItemsMapper hardwareItemsMapper;

    @BeforeEach
    public void setUp() {
        hardwareItemsMapper = new HardwareItemsMapperImpl();
    }
}
