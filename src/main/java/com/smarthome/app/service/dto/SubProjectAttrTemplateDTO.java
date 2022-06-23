package com.smarthome.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.smarthome.app.domain.SubProjectAttrTemplate} entity.
 */
public class SubProjectAttrTemplateDTO implements Serializable {

    private Long id;

    private String attrCode;

    private String attrCodeNameAr;

    private String attrCodeNameEn;

    private String attrType;

    private SubProjectTemplateDTO subProjectTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrCodeNameAr() {
        return attrCodeNameAr;
    }

    public void setAttrCodeNameAr(String attrCodeNameAr) {
        this.attrCodeNameAr = attrCodeNameAr;
    }

    public String getAttrCodeNameEn() {
        return attrCodeNameEn;
    }

    public void setAttrCodeNameEn(String attrCodeNameEn) {
        this.attrCodeNameEn = attrCodeNameEn;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
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
        if (!(o instanceof SubProjectAttrTemplateDTO)) {
            return false;
        }

        SubProjectAttrTemplateDTO subProjectAttrTemplateDTO = (SubProjectAttrTemplateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subProjectAttrTemplateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubProjectAttrTemplateDTO{" +
            "id=" + getId() +
            ", attrCode='" + getAttrCode() + "'" +
            ", attrCodeNameAr='" + getAttrCodeNameAr() + "'" +
            ", attrCodeNameEn='" + getAttrCodeNameEn() + "'" +
            ", attrType='" + getAttrType() + "'" +
            ", subProjectTemplate=" + getSubProjectTemplate() +
            "}";
    }
}
