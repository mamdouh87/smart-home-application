package com.smarthome.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.smarthome.app.domain.SubProjectAttr} entity.
 */
public class SubProjectAttrDTO implements Serializable {

    private Long id;

    private String attrCode;

    private String attrCodeNameAr;

    private String attrCodeNameEn;

    private String attrType;

    private String attrValue;

    private SubProjectDTO subProject;

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

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public SubProjectDTO getSubProject() {
        return subProject;
    }

    public void setSubProject(SubProjectDTO subProject) {
        this.subProject = subProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubProjectAttrDTO)) {
            return false;
        }

        SubProjectAttrDTO subProjectAttrDTO = (SubProjectAttrDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subProjectAttrDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubProjectAttrDTO{" +
            "id=" + getId() +
            ", attrCode='" + getAttrCode() + "'" +
            ", attrCodeNameAr='" + getAttrCodeNameAr() + "'" +
            ", attrCodeNameEn='" + getAttrCodeNameEn() + "'" +
            ", attrType='" + getAttrType() + "'" +
            ", attrValue='" + getAttrValue() + "'" +
            ", subProject=" + getSubProject() +
            "}";
    }
}
