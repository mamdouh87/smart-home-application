package com.smarthome.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A SubProjectAttr.
 */
@Entity
@Table(name = "sub_project_attr")
public class SubProjectAttr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "attr_code")
    private String attrCode;

    @Column(name = "attr_code_name_ar")
    private String attrCodeNameAr;

    @Column(name = "attr_code_name_en")
    private String attrCodeNameEn;

    @Column(name = "attr_type")
    private String attrType;

    @Column(name = "attr_value")
    private String attrValue;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subProjectAttrs", "subProjectItemsReqs", "project" }, allowSetters = true)
    private SubProject subProject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubProjectAttr id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrCode() {
        return this.attrCode;
    }

    public SubProjectAttr attrCode(String attrCode) {
        this.setAttrCode(attrCode);
        return this;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrCodeNameAr() {
        return this.attrCodeNameAr;
    }

    public SubProjectAttr attrCodeNameAr(String attrCodeNameAr) {
        this.setAttrCodeNameAr(attrCodeNameAr);
        return this;
    }

    public void setAttrCodeNameAr(String attrCodeNameAr) {
        this.attrCodeNameAr = attrCodeNameAr;
    }

    public String getAttrCodeNameEn() {
        return this.attrCodeNameEn;
    }

    public SubProjectAttr attrCodeNameEn(String attrCodeNameEn) {
        this.setAttrCodeNameEn(attrCodeNameEn);
        return this;
    }

    public void setAttrCodeNameEn(String attrCodeNameEn) {
        this.attrCodeNameEn = attrCodeNameEn;
    }

    public String getAttrType() {
        return this.attrType;
    }

    public SubProjectAttr attrType(String attrType) {
        this.setAttrType(attrType);
        return this;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrValue() {
        return this.attrValue;
    }

    public SubProjectAttr attrValue(String attrValue) {
        this.setAttrValue(attrValue);
        return this;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public SubProject getSubProject() {
        return this.subProject;
    }

    public void setSubProject(SubProject subProject) {
        this.subProject = subProject;
    }

    public SubProjectAttr subProject(SubProject subProject) {
        this.setSubProject(subProject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubProjectAttr)) {
            return false;
        }
        return id != null && id.equals(((SubProjectAttr) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubProjectAttr{" +
            "id=" + getId() +
            ", attrCode='" + getAttrCode() + "'" +
            ", attrCodeNameAr='" + getAttrCodeNameAr() + "'" +
            ", attrCodeNameEn='" + getAttrCodeNameEn() + "'" +
            ", attrType='" + getAttrType() + "'" +
            ", attrValue='" + getAttrValue() + "'" +
            "}";
    }
}
