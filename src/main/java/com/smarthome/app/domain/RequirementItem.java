package com.smarthome.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A RequirementItem.
 */
@Entity
@Table(name = "requirement_item")
public class RequirementItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sys_code")
    private String sysCode;

    @Column(name = "code")
    private String code;

    @Column(name = "description_ar")
    private String descriptionAr;

    @Column(name = "description_en")
    private String descriptionEn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subProjectAttrTemplates", "requirementItems", "projectTemplate" }, allowSetters = true)
    private SubProjectTemplate subProjectTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequirementItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public RequirementItem sysCode(String sysCode) {
        this.setSysCode(sysCode);
        return this;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getCode() {
        return this.code;
    }

    public RequirementItem code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescriptionAr() {
        return this.descriptionAr;
    }

    public RequirementItem descriptionAr(String descriptionAr) {
        this.setDescriptionAr(descriptionAr);
        return this;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getDescriptionEn() {
        return this.descriptionEn;
    }

    public RequirementItem descriptionEn(String descriptionEn) {
        this.setDescriptionEn(descriptionEn);
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public SubProjectTemplate getSubProjectTemplate() {
        return this.subProjectTemplate;
    }

    public void setSubProjectTemplate(SubProjectTemplate subProjectTemplate) {
        this.subProjectTemplate = subProjectTemplate;
    }

    public RequirementItem subProjectTemplate(SubProjectTemplate subProjectTemplate) {
        this.setSubProjectTemplate(subProjectTemplate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequirementItem)) {
            return false;
        }
        return id != null && id.equals(((RequirementItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequirementItem{" +
            "id=" + getId() +
            ", sysCode='" + getSysCode() + "'" +
            ", code='" + getCode() + "'" +
            ", descriptionAr='" + getDescriptionAr() + "'" +
            ", descriptionEn='" + getDescriptionEn() + "'" +
            "}";
    }
}
