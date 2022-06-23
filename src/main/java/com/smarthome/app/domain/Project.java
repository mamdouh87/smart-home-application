package com.smarthome.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "enter_date")
    private Instant enterDate;

    @Column(name = "location")
    private String location;

    @JsonIgnoreProperties(value = { "subProjectTemplates" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ProjectTemplate projectTemplate;

    @OneToOne
    @JoinColumn(unique = true)
    private BuildingType buildingType;

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties(value = { "subProjectAttrs", "subProjectItemsReqs", "project" }, allowSetters = true)
    private Set<SubProject> subProjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Project id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return this.clientName;
    }

    public Project clientName(String clientName) {
        this.setClientName(clientName);
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Instant getEnterDate() {
        return this.enterDate;
    }

    public Project enterDate(Instant enterDate) {
        this.setEnterDate(enterDate);
        return this;
    }

    public void setEnterDate(Instant enterDate) {
        this.enterDate = enterDate;
    }

    public String getLocation() {
        return this.location;
    }

    public Project location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProjectTemplate getProjectTemplate() {
        return this.projectTemplate;
    }

    public void setProjectTemplate(ProjectTemplate projectTemplate) {
        this.projectTemplate = projectTemplate;
    }

    public Project projectTemplate(ProjectTemplate projectTemplate) {
        this.setProjectTemplate(projectTemplate);
        return this;
    }

    public BuildingType getBuildingType() {
        return this.buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public Project buildingType(BuildingType buildingType) {
        this.setBuildingType(buildingType);
        return this;
    }

    public Set<SubProject> getSubProjects() {
        return this.subProjects;
    }

    public void setSubProjects(Set<SubProject> subProjects) {
        if (this.subProjects != null) {
            this.subProjects.forEach(i -> i.setProject(null));
        }
        if (subProjects != null) {
            subProjects.forEach(i -> i.setProject(this));
        }
        this.subProjects = subProjects;
    }

    public Project subProjects(Set<SubProject> subProjects) {
        this.setSubProjects(subProjects);
        return this;
    }

    public Project addSubProjects(SubProject subProject) {
        this.subProjects.add(subProject);
        subProject.setProject(this);
        return this;
    }

    public Project removeSubProjects(SubProject subProject) {
        this.subProjects.remove(subProject);
        subProject.setProject(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", clientName='" + getClientName() + "'" +
            ", enterDate='" + getEnterDate() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
