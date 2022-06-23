package com.smarthome.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A ProjectItemsRequirement.
 */
@Entity
@Table(name = "project_items_requirement")
public class ProjectItemsRequirement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "qty_no")
    private Integer qtyNo;

    @Column(name = "notes")
    private String notes;

    @JsonIgnoreProperties(value = { "subProjectTemplate" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private RequirementItem requirementItems;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subProjectAttrs", "subProjectItemsReqs", "project" }, allowSetters = true)
    private SubProject subProject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjectItemsRequirement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQtyNo() {
        return this.qtyNo;
    }

    public ProjectItemsRequirement qtyNo(Integer qtyNo) {
        this.setQtyNo(qtyNo);
        return this;
    }

    public void setQtyNo(Integer qtyNo) {
        this.qtyNo = qtyNo;
    }

    public String getNotes() {
        return this.notes;
    }

    public ProjectItemsRequirement notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public RequirementItem getRequirementItems() {
        return this.requirementItems;
    }

    public void setRequirementItems(RequirementItem requirementItem) {
        this.requirementItems = requirementItem;
    }

    public ProjectItemsRequirement requirementItems(RequirementItem requirementItem) {
        this.setRequirementItems(requirementItem);
        return this;
    }

    public SubProject getSubProject() {
        return this.subProject;
    }

    public void setSubProject(SubProject subProject) {
        this.subProject = subProject;
    }

    public ProjectItemsRequirement subProject(SubProject subProject) {
        this.setSubProject(subProject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectItemsRequirement)) {
            return false;
        }
        return id != null && id.equals(((ProjectItemsRequirement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectItemsRequirement{" +
            "id=" + getId() +
            ", qtyNo=" + getQtyNo() +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
