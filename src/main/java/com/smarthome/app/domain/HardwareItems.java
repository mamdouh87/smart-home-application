package com.smarthome.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A HardwareItems.
 */
@Entity
@Table(name = "hardware_items")
public class HardwareItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "hardware_desc_ar")
    private String hardwareDescAr;

    @Column(name = "hardware_desc_en")
    private String hardwareDescEn;

    @Column(name = "supported_qty")
    private Integer supportedQty;

    @JsonIgnoreProperties(value = { "subProjectTemplate" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private RequirementItem item;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HardwareItems id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHardwareDescAr() {
        return this.hardwareDescAr;
    }

    public HardwareItems hardwareDescAr(String hardwareDescAr) {
        this.setHardwareDescAr(hardwareDescAr);
        return this;
    }

    public void setHardwareDescAr(String hardwareDescAr) {
        this.hardwareDescAr = hardwareDescAr;
    }

    public String getHardwareDescEn() {
        return this.hardwareDescEn;
    }

    public HardwareItems hardwareDescEn(String hardwareDescEn) {
        this.setHardwareDescEn(hardwareDescEn);
        return this;
    }

    public void setHardwareDescEn(String hardwareDescEn) {
        this.hardwareDescEn = hardwareDescEn;
    }

    public Integer getSupportedQty() {
        return this.supportedQty;
    }

    public HardwareItems supportedQty(Integer supportedQty) {
        this.setSupportedQty(supportedQty);
        return this;
    }

    public void setSupportedQty(Integer supportedQty) {
        this.supportedQty = supportedQty;
    }

    public RequirementItem getItem() {
        return this.item;
    }

    public void setItem(RequirementItem requirementItem) {
        this.item = requirementItem;
    }

    public HardwareItems item(RequirementItem requirementItem) {
        this.setItem(requirementItem);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HardwareItems)) {
            return false;
        }
        return id != null && id.equals(((HardwareItems) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HardwareItems{" +
            "id=" + getId() +
            ", hardwareDescAr='" + getHardwareDescAr() + "'" +
            ", hardwareDescEn='" + getHardwareDescEn() + "'" +
            ", supportedQty=" + getSupportedQty() +
            "}";
    }
}
