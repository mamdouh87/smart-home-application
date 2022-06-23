package com.smarthome.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectTemplateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTemplate.class);
        ProjectTemplate projectTemplate1 = new ProjectTemplate();
        projectTemplate1.setId(1L);
        ProjectTemplate projectTemplate2 = new ProjectTemplate();
        projectTemplate2.setId(projectTemplate1.getId());
        assertThat(projectTemplate1).isEqualTo(projectTemplate2);
        projectTemplate2.setId(2L);
        assertThat(projectTemplate1).isNotEqualTo(projectTemplate2);
        projectTemplate1.setId(null);
        assertThat(projectTemplate1).isNotEqualTo(projectTemplate2);
    }
}
