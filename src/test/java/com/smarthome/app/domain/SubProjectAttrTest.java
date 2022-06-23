package com.smarthome.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubProjectAttrTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubProjectAttr.class);
        SubProjectAttr subProjectAttr1 = new SubProjectAttr();
        subProjectAttr1.setId(1L);
        SubProjectAttr subProjectAttr2 = new SubProjectAttr();
        subProjectAttr2.setId(subProjectAttr1.getId());
        assertThat(subProjectAttr1).isEqualTo(subProjectAttr2);
        subProjectAttr2.setId(2L);
        assertThat(subProjectAttr1).isNotEqualTo(subProjectAttr2);
        subProjectAttr1.setId(null);
        assertThat(subProjectAttr1).isNotEqualTo(subProjectAttr2);
    }
}
