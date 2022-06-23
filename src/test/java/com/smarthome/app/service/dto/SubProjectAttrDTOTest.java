package com.smarthome.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubProjectAttrDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubProjectAttrDTO.class);
        SubProjectAttrDTO subProjectAttrDTO1 = new SubProjectAttrDTO();
        subProjectAttrDTO1.setId(1L);
        SubProjectAttrDTO subProjectAttrDTO2 = new SubProjectAttrDTO();
        assertThat(subProjectAttrDTO1).isNotEqualTo(subProjectAttrDTO2);
        subProjectAttrDTO2.setId(subProjectAttrDTO1.getId());
        assertThat(subProjectAttrDTO1).isEqualTo(subProjectAttrDTO2);
        subProjectAttrDTO2.setId(2L);
        assertThat(subProjectAttrDTO1).isNotEqualTo(subProjectAttrDTO2);
        subProjectAttrDTO1.setId(null);
        assertThat(subProjectAttrDTO1).isNotEqualTo(subProjectAttrDTO2);
    }
}
