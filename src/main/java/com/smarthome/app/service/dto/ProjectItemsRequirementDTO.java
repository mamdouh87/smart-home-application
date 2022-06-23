package com.smarthome.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.smarthome.app.domain.ProjectItemsRequirement} entity.
 */
public class ProjectItemsRequirementDTO implements Serializable {

    private Long id;

    private Integer qtyNo;

    private String notes;

    private RequirementItemDTO requirementItems;

    private SubProjectDTO subProject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQtyNo() {
        return qtyNo;
    }

    public void setQtyNo(Integer qtyNo) {
        this.qtyNo = qtyNo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public RequirementItemDTO getRequirementItems() {
        return requirementItems;
    }

    public void setRequirementItems(RequirementItemDTO requirementItems) {
        this.requirementItems = requirementItems;
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
        if (!(o instanceof ProjectItemsRequirementDTO)) {
            return false;
        }

        ProjectItemsRequirementDTO projectItemsRequirementDTO = (ProjectItemsRequirementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectItemsRequirementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectItemsRequirementDTO{" +
            "id=" + getId() +
            ", qtyNo=" + getQtyNo() +
            ", notes='" + getNotes() + "'" +
            ", requirementItems=" + getRequirementItems() +
            ", subProject=" + getSubProject() +
            "}";
    }
}
