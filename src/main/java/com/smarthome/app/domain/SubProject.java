package com.smarthome.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A SubProject.
 */
@Entity
@Table(name = "sub_project")
public class SubProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "project_template_code")
    private String projectTemplateCode;

    @Column(name = "project_template_name_ar")
    private String projectTemplateNameAr;

    @Column(name = "project_template_name_en")
    private String projectTemplateNameEn;

    @OneToMany(mappedBy = "subProject")
    @JsonIgnoreProperties(value = { "subProject" }, allowSetters = true)
    private Set<SubProjectAttr> subProjectAttrs = new HashSet<>();

    @OneToMany(mappedBy = "subProject")
    @JsonIgnoreProperties(value = { "requirementItems", "subProject" }, allowSetters = true)
    private Set<ProjectItemsRequirement> subProjectItemsReqs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "projectTemplate", "buildingType", "subProjects" }, allowSetters = true)
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubProject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectTemplateCode() {
        return this.projectTemplateCode;
    }

    public SubProject projectTemplateCode(String projectTemplateCode) {
        this.setProjectTemplateCode(projectTemplateCode);
        return this;
    }

    public void setProjectTemplateCode(String projectTemplateCode) {
        this.projectTemplateCode = projectTemplateCode;
    }

    public String getProjectTemplateNameAr() {
        return this.projectTemplateNameAr;
    }

    public SubProject projectTemplateNameAr(String projectTemplateNameAr) {
        this.setProjectTemplateNameAr(projectTemplateNameAr);
        return this;
    }

    public void setProjectTemplateNameAr(String projectTemplateNameAr) {
        this.projectTemplateNameAr = projectTemplateNameAr;
    }

    public String getProjectTemplateNameEn() {
        return this.projectTemplateNameEn;
    }

    public SubProject projectTemplateNameEn(String projectTemplateNameEn) {
        this.setProjectTemplateNameEn(projectTemplateNameEn);
        return this;
    }

    public void setProjectTemplateNameEn(String projectTemplateNameEn) {
        this.projectTemplateNameEn = projectTemplateNameEn;
    }

    public Set<SubProjectAttr> getSubProjectAttrs() {
        return this.subProjectAttrs;
    }

    public void setSubProjectAttrs(Set<SubProjectAttr> subProjectAttrs) {
        if (this.subProjectAttrs != null) {
            this.subProjectAttrs.forEach(i -> i.setSubProject(null));
        }
        if (subProjectAttrs != null) {
            subProjectAttrs.forEach(i -> i.setSubProject(this));
        }
        this.subProjectAttrs = subProjectAttrs;
    }

    public SubProject subProjectAttrs(Set<SubProjectAttr> subProjectAttrs) {
        this.setSubProjectAttrs(subProjectAttrs);
        return this;
    }

    public SubProject addSubProjectAttrs(SubProjectAttr subProjectAttr) {
        this.subProjectAttrs.add(subProjectAttr);
        subProjectAttr.setSubProject(this);
        return this;
    }

    public SubProject removeSubProjectAttrs(SubProjectAttr subProjectAttr) {
        this.subProjectAttrs.remove(subProjectAttr);
        subProjectAttr.setSubProject(null);
        return this;
    }

    public Set<ProjectItemsRequirement> getSubProjectItemsReqs() {
        return this.subProjectItemsReqs;
    }

    public void setSubProjectItemsReqs(Set<ProjectItemsRequirement> projectItemsRequirements) {
        if (this.subProjectItemsReqs != null) {
            this.subProjectItemsReqs.forEach(i -> i.setSubProject(null));
        }
        if (projectItemsRequirements != null) {
            projectItemsRequirements.forEach(i -> i.setSubProject(this));
        }
        this.subProjectItemsReqs = projectItemsRequirements;
    }

    public SubProject subProjectItemsReqs(Set<ProjectItemsRequirement> projectItemsRequirements) {
        this.setSubProjectItemsReqs(projectItemsRequirements);
        return this;
    }

    public SubProject addSubProjectItemsReq(ProjectItemsRequirement projectItemsRequirement) {
        this.subProjectItemsReqs.add(projectItemsRequirement);
        projectItemsRequirement.setSubProject(this);
        return this;
    }

    public SubProject removeSubProjectItemsReq(ProjectItemsRequirement projectItemsRequirement) {
        this.subProjectItemsReqs.remove(projectItemsRequirement);
        projectItemsRequirement.setSubProject(null);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public SubProject project(Project project) {
        this.setProject(project);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubProject)) {
            return false;
        }
        return id != null && id.equals(((SubProject) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubProject{" +
            "id=" + getId() +
            ", projectTemplateCode='" + getProjectTemplateCode() + "'" +
            ", projectTemplateNameAr='" + getProjectTemplateNameAr() + "'" +
            ", projectTemplateNameEn='" + getProjectTemplateNameEn() + "'" +
            "}";
    }
}
