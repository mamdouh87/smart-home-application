package com.smarthome.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubProjectAttrTemplateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubProjectAttrTemplate.class);
        SubProjectAttrTemplate subProjectAttrTemplate1 = new SubProjectAttrTemplate();
        subProjectAttrTemplate1.setId(1L);
        SubProjectAttrTemplate subProjectAttrTemplate2 = new SubProjectAttrTemplate();
        subProjectAttrTemplate2.setId(subProjectAttrTemplate1.getId());
        assertThat(subProjectAttrTemplate1).isEqualTo(subProjectAttrTemplate2);
        subProjectAttrTemplate2.setId(2L);
        assertThat(subProjectAttrTemplate1).isNotEqualTo(subProjectAttrTemplate2);
        subProjectAttrTemplate1.setId(null);
        assertThat(subProjectAttrTemplate1).isNotEqualTo(subProjectAttrTemplate2);
    }
}
