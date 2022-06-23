package com.smarthome.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubProjectDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubProjectDTO.class);
        SubProjectDTO subProjectDTO1 = new SubProjectDTO();
        subProjectDTO1.setId(1L);
        SubProjectDTO subProjectDTO2 = new SubProjectDTO();
        assertThat(subProjectDTO1).isNotEqualTo(subProjectDTO2);
        subProjectDTO2.setId(subProjectDTO1.getId());
        assertThat(subProjectDTO1).isEqualTo(subProjectDTO2);
        subProjectDTO2.setId(2L);
        assertThat(subProjectDTO1).isNotEqualTo(subProjectDTO2);
        subProjectDTO1.setId(null);
        assertThat(subProjectDTO1).isNotEqualTo(subProjectDTO2);
    }
}
