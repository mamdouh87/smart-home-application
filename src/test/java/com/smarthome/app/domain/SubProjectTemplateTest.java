package com.smarthome.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubProjectTemplateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubProjectTemplate.class);
        SubProjectTemplate subProjectTemplate1 = new SubProjectTemplate();
        subProjectTemplate1.setId(1L);
        SubProjectTemplate subProjectTemplate2 = new SubProjectTemplate();
        subProjectTemplate2.setId(subProjectTemplate1.getId());
        assertThat(subProjectTemplate1).isEqualTo(subProjectTemplate2);
        subProjectTemplate2.setId(2L);
        assertThat(subProjectTemplate1).isNotEqualTo(subProjectTemplate2);
        subProjectTemplate1.setId(null);
        assertThat(subProjectTemplate1).isNotEqualTo(subProjectTemplate2);
    }
}
