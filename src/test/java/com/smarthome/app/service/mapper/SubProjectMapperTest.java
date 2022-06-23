package com.smarthome.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubProjectMapperTest {

    private SubProjectMapper subProjectMapper;

    @BeforeEach
    public void setUp() {
        subProjectMapper = new SubProjectMapperImpl();
    }
}
