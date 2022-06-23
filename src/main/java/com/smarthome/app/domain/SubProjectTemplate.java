package com.smarthome.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A SubProjectTemplate.
 */
@Entity
@Table(name = "sub_project_template")
public class SubProjectTemplate implements Serializable {

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

    @OneToMany(mappedBy = "subProjectTemplate")
    @JsonIgnoreProperties(value = { "subProjectTemplate" }, allowSetters = true)
    private Set<SubProjectAttrTemplate> subProjectAttrTemplates = new HashSet<>();

    @OneToMany(mappedBy = "subProjectTemplate")
    @JsonIgnoreProperties(value = { "subProjectTemplate" }, allowSetters = true)
    private Set<RequirementItem> requirementItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "subProjectTemplates" }, allowSetters = true)
    private ProjectTemplate projectTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubProjectTemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectTemplateCode() {
        return this.projectTemplateCode;
    }

    public SubProjectTemplate projectTemplateCode(String projectTemplateCode) {
        this.setProjectTemplateCode(projectTemplateCode);
        return this;
    }

    public void setProjectTemplateCode(String projectTemplateCode) {
        this.projectTemplateCode = projectTemplateCode;
    }

    public String getProjectTemplateNameAr() {
        return this.projectTemplateNameAr;
    }

    public SubProjectTemplate projectTemplateNameAr(String projectTemplateNameAr) {
        this.setProjectTemplateNameAr(projectTemplateNameAr);
        return this;
    }

    public void setProjectTemplateNameAr(String projectTemplateNameAr) {
        this.projectTemplateNameAr = projectTemplateNameAr;
    }

    public String getProjectTemplateNameEn() {
        return this.projectTemplateNameEn;
    }

    public SubProjectTemplate projectTemplateNameEn(String projectTemplateNameEn) {
        this.setProjectTemplateNameEn(projectTemplateNameEn);
        return this;
    }

    public void setProjectTemplateNameEn(String projectTemplateNameEn) {
        this.projectTemplateNameEn = projectTemplateNameEn;
    }

    public Set<SubProjectAttrTemplate> getSubProjectAttrTemplates() {
        return this.subProjectAttrTemplates;
    }

    public void setSubProjectAttrTemplates(Set<SubProjectAttrTemplate> subProjectAttrTemplates) {
        if (this.subProjectAttrTemplates != null) {
            this.subProjectAttrTemplates.forEach(i -> i.setSubProjectTemplate(null));
        }
        if (subProjectAttrTemplates != null) {
            subProjectAttrTemplates.forEach(i -> i.setSubProjectTemplate(this));
        }
        this.subProjectAttrTemplates = subProjectAttrTemplates;
    }

    public SubProjectTemplate subProjectAttrTemplates(Set<SubProjectAttrTemplate> subProjectAttrTemplates) {
        this.setSubProjectAttrTemplates(subProjectAttrTemplates);
        return this;
    }

    public SubProjectTemplate addSubProjectAttrTemplate(SubProjectAttrTemplate subProjectAttrTemplate) {
        this.subProjectAttrTemplates.add(subProjectAttrTemplate);
        subProjectAttrTemplate.setSubProjectTemplate(this);
        return this;
    }

    public SubProjectTemplate removeSubProjectAttrTemplate(SubProjectAttrTemplate subProjectAttrTemplate) {
        this.subProjectAttrTemplates.remove(subProjectAttrTemplate);
        subProjectAttrTemplate.setSubProjectTemplate(null);
        return this;
    }

    public Set<RequirementItem> getRequirementItems() {
        return this.requirementItems;
    }

    public void setRequirementItems(Set<RequirementItem> requirementItems) {
        if (this.requirementItems != null) {
            this.requirementItems.forEach(i -> i.setSubProjectTemplate(null));
        }
        if (requirementItems != null) {
            requirementItems.forEach(i -> i.setSubProjectTemplate(this));
        }
        this.requirementItems = requirementItems;
    }

    public SubProjectTemplate requirementItems(Set<RequirementItem> requirementItems) {
        this.setRequirementItems(requirementItems);
        return this;
    }

    public SubProjectTemplate addRequirementItems(RequirementItem requirementItem) {
        this.requirementItems.add(requirementItem);
        requirementItem.setSubProjectTemplate(this);
        return this;
    }

    public SubProjectTemplate removeRequirementItems(RequirementItem requirementItem) {
        this.requirementItems.remove(requirementItem);
        requirementItem.setSubProjectTemplate(null);
        return this;
    }

    public ProjectTemplate getProjectTemplate() {
        return this.projectTemplate;
    }

    public void setProjectTemplate(ProjectTemplate projectTemplate) {
        this.projectTemplate = projectTemplate;
    }

    public SubProjectTemplate projectTemplate(ProjectTemplate projectTemplate) {
        this.setProjectTemplate(projectTemplate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubProjectTemplate)) {
            return false;
        }
        return id != null && id.equals(((SubProjectTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubProjectTemplate{" +
            "id=" + getId() +
            ", projectTemplateCode='" + getProjectTemplateCode() + "'" +
            ", projectTemplateNameAr='" + getProjectTemplateNameAr() + "'" +
            ", projectTemplateNameEn='" + getProjectTemplateNameEn() + "'" +
            "}";
    }
}
