package com.bamplee.chomi.api.application;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum TrafficType {
    WALK(3, "WALK"),
    BUS(2, "BUS"),
    SUBWAY(1, "SUBWAY");

    TrafficType(Integer trafficTypeNum, String trafficTypeName) {
        this.trafficTypeNum = trafficTypeNum;
        this.trafficTypeName = trafficTypeName;
    }

    private Integer trafficTypeNum;
    private String trafficTypeName;

    public Integer getTrafficTypeNum() {
        return trafficTypeNum;
    }

    public String getTrafficTypeName() {
        return trafficTypeName;
    }

    public static TrafficType getTrafficTypeByNum(Integer trafficTypeNum) {
        return Arrays.stream(TrafficType.values())
                     .filter(x -> x.getTrafficTypeNum().equals(trafficTypeNum))
                     .findFirst()
                     .orElseThrow(() -> new NoSuchElementException("존재하지 않는 대중교통 유형 입니다."));
    }
}
