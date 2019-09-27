package com.bamplee.chomi.api.application;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum TrafficType {
    WALK(3, "WALK", " 출발", "까지 걷기"),
    BUS(2, "BUS", " 승차", " 하차"),
    SUBWAY(1, "SUBWAY", " 승차", " 하차"),
    CAR(4, "CAR", "", " 주차");

    TrafficType(Integer trafficTypeNum, String trafficTypeName, String startStr, String endStr) {
        this.trafficTypeNum = trafficTypeNum;
        this.trafficTypeName = trafficTypeName;
        this.startStr = startStr;
        this.endStr = endStr;
    }

    private Integer trafficTypeNum;
    private String trafficTypeName;
    private String startStr;
    private String endStr;

    public String getStartStr() {
        return startStr;
    }

    public String getEndStr() {
        return endStr;
    }

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

    public static TrafficType getTrafficTypeByName(String trafficTypeName) {
        return Arrays.stream(TrafficType.values())
                     .filter(x -> x.getTrafficTypeName().equals(trafficTypeName))
                     .findFirst()
                     .orElseThrow(() -> new NoSuchElementException("존재하지 않는 대중교통 유형 입니다."));
    }
}
