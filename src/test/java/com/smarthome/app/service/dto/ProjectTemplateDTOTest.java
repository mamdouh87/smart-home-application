package com.smarthome.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectTemplateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTemplateDTO.class);
        ProjectTemplateDTO projectTemplateDTO1 = new ProjectTemplateDTO();
        projectTemplateDTO1.setId(1L);
        ProjectTemplateDTO projectTemplateDTO2 = new ProjectTemplateDTO();
        assertThat(projectTemplateDTO1).isNotEqualTo(projectTemplateDTO2);
        projectTemplateDTO2.setId(projectTemplateDTO1.getId());
        assertThat(projectTemplateDTO1).isEqualTo(projectTemplateDTO2);
        projectTemplateDTO2.setId(2L);
        assertThat(projectTemplateDTO1).isNotEqualTo(projectTemplateDTO2);
        projectTemplateDTO1.setId(null);
        assertThat(projectTemplateDTO1).isNotEqualTo(projectTemplateDTO2);
    }
}
