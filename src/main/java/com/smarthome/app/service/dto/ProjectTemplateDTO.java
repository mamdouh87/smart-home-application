package com.smarthome.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.smarthome.app.domain.ProjectTemplate} entity.
 */
public class ProjectTemplateDTO implements Serializable {

    private Long id;

    private String projectTemplateCode;

    private String projectTemplateNameAr;

    private String projectTemplateNameEn;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectTemplateDTO)) {
            return false;
        }

        ProjectTemplateDTO projectTemplateDTO = (ProjectTemplateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectTemplateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTemplateDTO{" +
            "id=" + getId() +
            ", projectTemplateCode='" + getProjectTemplateCode() + "'" +
            ", projectTemplateNameAr='" + getProjectTemplateNameAr() + "'" +
            ", projectTemplateNameEn='" + getProjectTemplateNameEn() + "'" +
            "}";
    }
}
