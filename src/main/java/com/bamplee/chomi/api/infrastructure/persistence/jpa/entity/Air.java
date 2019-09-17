package com.bamplee.chomi.api.infrastructure.persistence.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Air extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -14238791237843142L;

    public Air() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "AREA_NAME")
    private String areaName;

    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "AIR_VALUE")
    private Integer airValue;

    @Column(name = "AIR_GRADE")
    private String airGrade;

    @Column(name = "DATA_TIME")
    private LocalDateTime dataTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getAirGrade() {
        return airGrade;
    }

    public String getItemCode() {
        return itemCode;
    }

    public Integer getAirValue() {
        return airValue;
    }

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public static class Builder {
        private final String areaName;
        private final Integer airvalue;
        private final String airGrade;
        private final LocalDateTime dateTime;
        private String itemCode = "PM10";

        public Builder(String areaName, Integer airvalue, String airGrade, LocalDateTime dateTime) {
            this.areaName = areaName;
            this.airvalue = airvalue;
            this.airGrade = airGrade;
            this.dateTime = dateTime;
        }

        public Builder itemCode(String item) {
            itemCode = item;
            return this;
        }

        public Air build() {
            return new Air(this);
        }
    }

    public Air(Builder builder) {
        areaName = builder.areaName;
        itemCode = builder.itemCode;
        airValue = builder.airvalue;
        airGrade = builder.airGrade;
        dataTime = builder.dateTime;
    }
}
