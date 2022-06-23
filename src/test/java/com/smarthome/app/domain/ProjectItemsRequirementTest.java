package com.smarthome.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectItemsRequirementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectItemsRequirement.class);
        ProjectItemsRequirement projectItemsRequirement1 = new ProjectItemsRequirement();
        projectItemsRequirement1.setId(1L);
        ProjectItemsRequirement projectItemsRequirement2 = new ProjectItemsRequirement();
        projectItemsRequirement2.setId(projectItemsRequirement1.getId());
        assertThat(projectItemsRequirement1).isEqualTo(projectItemsRequirement2);
        projectItemsRequirement2.setId(2L);
        assertThat(projectItemsRequirement1).isNotEqualTo(projectItemsRequirement2);
        projectItemsRequirement1.setId(null);
        assertThat(projectItemsRequirement1).isNotEqualTo(projectItemsRequirement2);
    }
}
