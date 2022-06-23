package com.smarthome.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.smarthome.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequirementItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequirementItemDTO.class);
        RequirementItemDTO requirementItemDTO1 = new RequirementItemDTO();
        requirementItemDTO1.setId(1L);
        RequirementItemDTO requirementItemDTO2 = new RequirementItemDTO();
        assertThat(requirementItemDTO1).isNotEqualTo(requirementItemDTO2);
        requirementItemDTO2.setId(requirementItemDTO1.getId());
        assertThat(requirementItemDTO1).isEqualTo(requirementItemDTO2);
        requirementItemDTO2.setId(2L);
        assertThat(requirementItemDTO1).isNotEqualTo(requirementItemDTO2);
        requirementItemDTO1.setId(null);
        assertThat(requirementItemDTO1).isNotEqualTo(requirementItemDTO2);
    }
}
