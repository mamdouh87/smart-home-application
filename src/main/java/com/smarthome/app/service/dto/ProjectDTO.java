package com.smarthome.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.smarthome.app.domain.Project} entity.
 */
public class ProjectDTO implements Serializable {

    private Long id;

    private String clientName;

    private Instant enterDate;

    private String location;

    private ProjectTemplateDTO projectTemplate;

    private BuildingTypeDTO buildingType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Instant getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Instant enterDate) {
        this.enterDate = enterDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProjectTemplateDTO getProjectTemplate() {
        return projectTemplate;
    }

    public void setProjectTemplate(ProjectTemplateDTO projectTemplate) {
        this.projectTemplate = projectTemplate;
    }

    public BuildingTypeDTO getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingTypeDTO buildingType) {
        this.buildingType = buildingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectDTO)) {
            return false;
        }

        ProjectDTO projectDTO = (ProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + getId() +
            ", clientName='" + getClientName() + "'" +
            ", enterDate='" + getEnterDate() + "'" +
            ", location='" + getLocation() + "'" +
            ", projectTemplate=" + getProjectTemplate() +
            ", buildingType=" + getBuildingType() +
            "}";
    }
}
