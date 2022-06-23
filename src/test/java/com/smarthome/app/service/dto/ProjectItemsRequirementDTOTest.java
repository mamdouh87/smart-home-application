package com.smarthome.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectItemsRequirementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectItemsRequirementDTO.class);
        ProjectItemsRequirementDTO projectItemsRequirementDTO1 = new ProjectItemsRequirementDTO();
        projectItemsRequirementDTO1.setId(1L);
        ProjectItemsRequirementDTO projectItemsRequirementDTO2 = new ProjectItemsRequirementDTO();
        assertThat(projectItemsRequirementDTO1).isNotEqualTo(projectItemsRequirementDTO2);
        projectItemsRequirementDTO2.setId(projectItemsRequirementDTO1.getId());
        assertThat(projectItemsRequirementDTO1).isEqualTo(projectItemsRequirementDTO2);
        projectItemsRequirementDTO2.setId(2L);
        assertThat(projectItemsRequirementDTO1).isNotEqualTo(projectItemsRequirementDTO2);
        projectItemsRequirementDTO1.setId(null);
        assertThat(projectItemsRequirementDTO1).isNotEqualTo(projectItemsRequirementDTO2);
    }
}
