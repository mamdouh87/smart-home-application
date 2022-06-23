package com.smarthome.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HardwareItemsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HardwareItems.class);
        HardwareItems hardwareItems1 = new HardwareItems();
        hardwareItems1.setId(1L);
        HardwareItems hardwareItems2 = new HardwareItems();
        hardwareItems2.setId(hardwareItems1.getId());
        assertThat(hardwareItems1).isEqualTo(hardwareItems2);
        hardwareItems2.setId(2L);
        assertThat(hardwareItems1).isNotEqualTo(hardwareItems2);
        hardwareItems1.setId(null);
        assertThat(hardwareItems1).isNotEqualTo(hardwareItems2);
    }
}
