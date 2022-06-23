package com.smarthome.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuildingTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingType.class);
        BuildingType buildingType1 = new BuildingType();
        buildingType1.setId(1L);
        BuildingType buildingType2 = new BuildingType();
        buildingType2.setId(buildingType1.getId());
        assertThat(buildingType1).isEqualTo(buildingType2);
        buildingType2.setId(2L);
        assertThat(buildingType1).isNotEqualTo(buildingType2);
        buildingType1.setId(null);
        assertThat(buildingType1).isNotEqualTo(buildingType2);
    }
}
