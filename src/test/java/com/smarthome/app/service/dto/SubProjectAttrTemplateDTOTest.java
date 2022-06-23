package com.smarthome.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubProjectAttrTemplateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubProjectAttrTemplateDTO.class);
        SubProjectAttrTemplateDTO subProjectAttrTemplateDTO1 = new SubProjectAttrTemplateDTO();
        subProjectAttrTemplateDTO1.setId(1L);
        SubProjectAttrTemplateDTO subProjectAttrTemplateDTO2 = new SubProjectAttrTemplateDTO();
        assertThat(subProjectAttrTemplateDTO1).isNotEqualTo(subProjectAttrTemplateDTO2);
        subProjectAttrTemplateDTO2.setId(subProjectAttrTemplateDTO1.getId());
        assertThat(subProjectAttrTemplateDTO1).isEqualTo(subProjectAttrTemplateDTO2);
        subProjectAttrTemplateDTO2.setId(2L);
        assertThat(subProjectAttrTemplateDTO1).isNotEqualTo(subProjectAttrTemplateDTO2);
        subProjectAttrTemplateDTO1.setId(null);
        assertThat(subProjectAttrTemplateDTO1).isNotEqualTo(subProjectAttrTemplateDTO2);
    }
}
