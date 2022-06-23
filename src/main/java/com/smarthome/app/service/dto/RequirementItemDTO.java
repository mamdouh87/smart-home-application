package com.smarthome.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.smarthome.app.domain.RequirementItem} entity.
 */
public class RequirementItemDTO implements Serializable {

    private Long id;

    private String sysCode;

    private String code;

    private String descriptionAr;

    private String descriptionEn;

    private SubProjectTemplateDTO subProjectTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public SubProjectTemplateDTO getSubProjectTemplate() {
        return subProjectTemplate;
    }

    public void setSubProjectTemplate(SubProjectTemplateDTO subProjectTemplate) {
        this.subProjectTemplate = subProjectTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequirementItemDTO)) {
            return false;
        }

        RequirementItemDTO requirementItemDTO = (RequirementItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requirementItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequirementItemDTO{" +
            "id=" + getId() +
            ", sysCode='" + getSysCode() + "'" +
            ", code='" + getCode() + "'" +
            ", descriptionAr='" + getDescriptionAr() + "'" +
            ", descriptionEn='" + getDescriptionEn() + "'" +
            ", subProjectTemplate=" + getSubProjectTemplate() +
            "}";
    }
}
