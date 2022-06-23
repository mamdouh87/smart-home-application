package com.smarthome.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequirementItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequirementItem.class);
        RequirementItem requirementItem1 = new RequirementItem();
        requirementItem1.setId(1L);
        RequirementItem requirementItem2 = new RequirementItem();
        requirementItem2.setId(requirementItem1.getId());
        assertThat(requirementItem1).isEqualTo(requirementItem2);
        requirementItem2.setId(2L);
        assertThat(requirementItem1).isNotEqualTo(requirementItem2);
        requirementItem1.setId(null);
        assertThat(requirementItem1).isNotEqualTo(requirementItem2);
    }
}
