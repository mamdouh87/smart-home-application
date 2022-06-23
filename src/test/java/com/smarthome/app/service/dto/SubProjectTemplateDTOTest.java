package com.smarthome.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubProjectTemplateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubProjectTemplateDTO.class);
        SubProjectTemplateDTO subProjectTemplateDTO1 = new SubProjectTemplateDTO();
        subProjectTemplateDTO1.setId(1L);
        SubProjectTemplateDTO subProjectTemplateDTO2 = new SubProjectTemplateDTO();
        assertThat(subProjectTemplateDTO1).isNotEqualTo(subProjectTemplateDTO2);
        subProjectTemplateDTO2.setId(subProjectTemplateDTO1.getId());
        assertThat(subProjectTemplateDTO1).isEqualTo(subProjectTemplateDTO2);
        subProjectTemplateDTO2.setId(2L);
        assertThat(subProjectTemplateDTO1).isNotEqualTo(subProjectTemplateDTO2);
        subProjectTemplateDTO1.setId(null);
        assertThat(subProjectTemplateDTO1).isNotEqualTo(subProjectTemplateDTO2);
    }
}
