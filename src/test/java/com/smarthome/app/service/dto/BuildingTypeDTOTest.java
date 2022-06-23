package com.smarthome.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuildingTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingTypeDTO.class);
        BuildingTypeDTO buildingTypeDTO1 = new BuildingTypeDTO();
        buildingTypeDTO1.setId(1L);
        BuildingTypeDTO buildingTypeDTO2 = new BuildingTypeDTO();
        assertThat(buildingTypeDTO1).isNotEqualTo(buildingTypeDTO2);
        buildingTypeDTO2.setId(buildingTypeDTO1.getId());
        assertThat(buildingTypeDTO1).isEqualTo(buildingTypeDTO2);
        buildingTypeDTO2.setId(2L);
        assertThat(buildingTypeDTO1).isNotEqualTo(buildingTypeDTO2);
        buildingTypeDTO1.setId(null);
        assertThat(buildingTypeDTO1).isNotEqualTo(buildingTypeDTO2);
    }
}
