package com.smarthome.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.smarthome.app.domain.SubProjectTemplate} entity.
 */
public class SubProjectTemplateDTO implements Serializable {

    private Long id;

    private String projectTemplateCode;

    private String projectTemplateNameAr;

    private String projectTemplateNameEn;

    private ProjectTemplateDTO projectTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectTemplateCode() {
        return projectTemplateCode;
    }

    public void setProjectTemplateCode(String projectTemplateCode) {
        this.projectTemplateCode = projectTemplateCode;
    }

    public String getProjectTemplateNameAr() {
        return projectTemplateNameAr;
    }

    public void setProjectTemplateNameAr(String projectTemplateNameAr) {
        this.projectTemplateNameAr = projectTemplateNameAr;
    }

    public String getProjectTemplateNameEn() {
        return projectTemplateNameEn;
    }

    public void setProjectTemplateNameEn(String projectTemplateNameEn) {
        this.projectTemplateNameEn = projectTemplateNameEn;
    }

    public ProjectTemplateDTO getProjectTemplate() {
        return projectTemplate;
    }

    public void setProjectTemplate(ProjectTemplateDTO projectTemplate) {
        this.projectTemplate = projectTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubProjectTemplateDTO)) {
            return false;
        }

        SubProjectTemplateDTO subProjectTemplateDTO = (SubProjectTemplateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subProjectTemplateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubProjectTemplateDTO{" +
            "id=" + getId() +
            ", projectTemplateCode='" + getProjectTemplateCode() + "'" +
            ", projectTemplateNameAr='" + getProjectTemplateNameAr() + "'" +
            ", projectTemplateNameEn='" + getProjectTemplateNameEn() + "'" +
            ", projectTemplate=" + getProjectTemplate() +
            "}";
    }
}
