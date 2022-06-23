package com.smarthome.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ProjectTemplate.
 */
@Entity
@Table(name = "project_template")
public class ProjectTemplate implements Serializable {

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

    @OneToMany(mappedBy = "projectTemplate")
    @JsonIgnoreProperties(value = { "subProjectAttrTemplates", "requirementItems", "projectTemplate" }, allowSetters = true)
    private Set<SubProjectTemplate> subProjectTemplates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjectTemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectTemplateCode() {
        return this.projectTemplateCode;
    }

    public ProjectTemplate projectTemplateCode(String projectTemplateCode) {
        this.setProjectTemplateCode(projectTemplateCode);
        return this;
    }

    public void setProjectTemplateCode(String projectTemplateCode) {
        this.projectTemplateCode = projectTemplateCode;
    }

    public String getProjectTemplateNameAr() {
        return this.projectTemplateNameAr;
    }

    public ProjectTemplate projectTemplateNameAr(String projectTemplateNameAr) {
        this.setProjectTemplateNameAr(projectTemplateNameAr);
        return this;
    }

    public void setProjectTemplateNameAr(String projectTemplateNameAr) {
        this.projectTemplateNameAr = projectTemplateNameAr;
    }

    public String getProjectTemplateNameEn() {
        return this.projectTemplateNameEn;
    }

    public ProjectTemplate projectTemplateNameEn(String projectTemplateNameEn) {
        this.setProjectTemplateNameEn(projectTemplateNameEn);
        return this;
    }

    public void setProjectTemplateNameEn(String projectTemplateNameEn) {
        this.projectTemplateNameEn = projectTemplateNameEn;
    }

    public Set<SubProjectTemplate> getSubProjectTemplates() {
        return this.subProjectTemplates;
    }

    public void setSubProjectTemplates(Set<SubProjectTemplate> subProjectTemplates) {
        if (this.subProjectTemplates != null) {
            this.subProjectTemplates.forEach(i -> i.setProjectTemplate(null));
        }
        if (subProjectTemplates != null) {
            subProjectTemplates.forEach(i -> i.setProjectTemplate(this));
        }
        this.subProjectTemplates = subProjectTemplates;
    }

    public ProjectTemplate subProjectTemplates(Set<SubProjectTemplate> subProjectTemplates) {
        this.setSubProjectTemplates(subProjectTemplates);
        return this;
    }

    public ProjectTemplate addSubProjectTemplate(SubProjectTemplate subProjectTemplate) {
        this.subProjectTemplates.add(subProjectTemplate);
        subProjectTemplate.setProjectTemplate(this);
        return this;
    }

    public ProjectTemplate removeSubProjectTemplate(SubProjectTemplate subProjectTemplate) {
        this.subProjectTemplates.remove(subProjectTemplate);
        subProjectTemplate.setProjectTemplate(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectTemplate)) {
            return false;
        }
        return id != null && id.equals(((ProjectTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTemplate{" +
            "id=" + getId() +
            ", projectTemplateCode='" + getProjectTemplateCode() + "'" +
            ", projectTemplateNameAr='" + getProjectTemplateNameAr() + "'" +
            ", projectTemplateNameEn='" + getProjectTemplateNameEn() + "'" +
            "}";
    }
}
