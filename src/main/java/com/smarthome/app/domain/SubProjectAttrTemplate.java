package com.smarthome.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A SubProjectAttrTemplate.
 */
@Entity
@Table(name = "sub_project_attr_template")
public class SubProjectAttrTemplate implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "subProjectAttrTemplates", "requirementItems", "projectTemplate" }, allowSetters = true)
    private SubProjectTemplate subProjectTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubProjectAttrTemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrCode() {
        return this.attrCode;
    }

    public SubProjectAttrTemplate attrCode(String attrCode) {
        this.setAttrCode(attrCode);
        return this;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrCodeNameAr() {
        return this.attrCodeNameAr;
    }

    public SubProjectAttrTemplate attrCodeNameAr(String attrCodeNameAr) {
        this.setAttrCodeNameAr(attrCodeNameAr);
        return this;
    }

    public void setAttrCodeNameAr(String attrCodeNameAr) {
        this.attrCodeNameAr = attrCodeNameAr;
    }

    public String getAttrCodeNameEn() {
        return this.attrCodeNameEn;
    }

    public SubProjectAttrTemplate attrCodeNameEn(String attrCodeNameEn) {
        this.setAttrCodeNameEn(attrCodeNameEn);
        return this;
    }

    public void setAttrCodeNameEn(String attrCodeNameEn) {
        this.attrCodeNameEn = attrCodeNameEn;
    }

    public String getAttrType() {
        return this.attrType;
    }

    public SubProjectAttrTemplate attrType(String attrType) {
        this.setAttrType(attrType);
        return this;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public SubProjectTemplate getSubProjectTemplate() {
        return this.subProjectTemplate;
    }

    public void setSubProjectTemplate(SubProjectTemplate subProjectTemplate) {
        this.subProjectTemplate = subProjectTemplate;
    }

    public SubProjectAttrTemplate subProjectTemplate(SubProjectTemplate subProjectTemplate) {
        this.setSubProjectTemplate(subProjectTemplate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubProjectAttrTemplate)) {
            return false;
        }
        return id != null && id.equals(((SubProjectAttrTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubProjectAttrTemplate{" +
            "id=" + getId() +
            ", attrCode='" + getAttrCode() + "'" +
            ", attrCodeNameAr='" + getAttrCodeNameAr() + "'" +
            ", attrCodeNameEn='" + getAttrCodeNameEn() + "'" +
            ", attrType='" + getAttrType() + "'" +
            "}";
    }
}
