package com.smarthome.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.smarthome.app.domain.HardwareItems} entity.
 */
public class HardwareItemsDTO implements Serializable {

    private Long id;

    private String hardwareDescAr;

    private String hardwareDescEn;

    private Integer supportedQty;

    private RequirementItemDTO item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHardwareDescAr() {
        return hardwareDescAr;
    }

    public void setHardwareDescAr(String hardwareDescAr) {
        this.hardwareDescAr = hardwareDescAr;
    }

    public String getHardwareDescEn() {
        return hardwareDescEn;
    }

    public void setHardwareDescEn(String hardwareDescEn) {
        this.hardwareDescEn = hardwareDescEn;
    }

    public Integer getSupportedQty() {
        return supportedQty;
    }

    public void setSupportedQty(Integer supportedQty) {
        this.supportedQty = supportedQty;
    }

    public RequirementItemDTO getItem() {
        return item;
    }

    public void setItem(RequirementItemDTO item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HardwareItemsDTO)) {
            return false;
        }

        HardwareItemsDTO hardwareItemsDTO = (HardwareItemsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hardwareItemsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HardwareItemsDTO{" +
            "id=" + getId() +
            ", hardwareDescAr='" + getHardwareDescAr() + "'" +
            ", hardwareDescEn='" + getHardwareDescEn() + "'" +
            ", supportedQty=" + getSupportedQty() +
            ", item=" + getItem() +
            "}";
    }
}
