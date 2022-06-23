package com.smarthome.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubProjectAttrMapperTest {

    private SubProjectAttrMapper subProjectAttrMapper;

    @BeforeEach
    public void setUp() {
        subProjectAttrMapper = new SubProjectAttrMapperImpl();
    }
}
