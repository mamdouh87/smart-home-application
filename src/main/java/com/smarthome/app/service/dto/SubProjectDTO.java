package com.smarthome.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.smarthome.app.domain.SubProject} entity.
 */
public class SubProjectDTO implements Serializable {

    private Long id;

    private String projectTemplateCode;

    private String projectTemplateNameAr;

    private String projectTemplateNameEn;

    private ProjectDTO project;

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

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubProjectDTO)) {
            return false;
        }

        SubProjectDTO subProjectDTO = (SubProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subProjectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubProjectDTO{" +
            "id=" + getId() +
            ", projectTemplateCode='" + getProjectTemplateCode() + "'" +
            ", projectTemplateNameAr='" + getProjectTemplateNameAr() + "'" +
            ", projectTemplateNameEn='" + getProjectTemplateNameEn() + "'" +
            ", project=" + getProject() +
            "}";
    }
}
