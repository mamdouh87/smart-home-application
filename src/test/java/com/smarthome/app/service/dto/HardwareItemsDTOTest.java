package com.smarthome.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HardwareItemsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HardwareItemsDTO.class);
        HardwareItemsDTO hardwareItemsDTO1 = new HardwareItemsDTO();
        hardwareItemsDTO1.setId(1L);
        HardwareItemsDTO hardwareItemsDTO2 = new HardwareItemsDTO();
        assertThat(hardwareItemsDTO1).isNotEqualTo(hardwareItemsDTO2);
        hardwareItemsDTO2.setId(hardwareItemsDTO1.getId());
        assertThat(hardwareItemsDTO1).isEqualTo(hardwareItemsDTO2);
        hardwareItemsDTO2.setId(2L);
        assertThat(hardwareItemsDTO1).isNotEqualTo(hardwareItemsDTO2);
        hardwareItemsDTO1.setId(null);
        assertThat(hardwareItemsDTO1).isNotEqualTo(hardwareItemsDTO2);
    }
}
