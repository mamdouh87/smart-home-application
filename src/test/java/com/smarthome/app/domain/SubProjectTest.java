package com.smarthome.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubProjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubProject.class);
        SubProject subProject1 = new SubProject();
        subProject1.setId(1L);
        SubProject subProject2 = new SubProject();
        subProject2.setId(subProject1.getId());
        assertThat(subProject1).isEqualTo(subProject2);
        subProject2.setId(2L);
        assertThat(subProject1).isNotEqualTo(subProject2);
        subProject1.setId(null);
        assertThat(subProject1).isNotEqualTo(subProject2);
    }
}
