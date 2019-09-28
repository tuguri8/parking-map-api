package com.bamplee.chomi.api.application;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;

public enum TrafficType {
    WALK(3, "WALK", " 출발", "까지 걷기", detailPath -> null, detailPath -> {
        StringBuffer sb = new StringBuffer();
        return sb.append(detailPath.getSectionTime()).append("분, ").append(detailPath.getDistance()).append("m").toString();
    }),
    BUS(2, "BUS", " 승차", " 하차", detailPath -> {
        StringBuffer sb = new StringBuffer();
        return sb.append(detailPath.getLane().get(0).getBusNo())
                 .append("번, ")
                 .append(detailPath.getStationCount())
                 .append("개 정류장 이동")
                 .toString();
    }, detailPath -> null),
    SUBWAY(1, "SUBWAY", " 승차", " 하차", detailPath -> {
        StringBuffer sb = new StringBuffer();
        return sb.append(detailPath.getLane().get(0).getName())
                 .append(", ")
                 .append(detailPath.getStationCount())
                 .append("개 역 이동")
                 .toString();
    }, detailPath -> null),
    CAR(4, "CAR", "", " 주차", detailPath -> {
        StringBuffer sb = new StringBuffer();
        return sb.append(detailPath.getSectionTime())
                 .append("분, ")
                 .append(detailPath.getDistance() / 1000)
                 .append("km, ")
                 .append(detailPath.getFuelPrice())
                 .append("원")
                 .toString();
    }, detailPath -> {
        StringBuffer sb = new StringBuffer();
        return sb.append(detailPath.getDriveRoute().getParkingInfo().getTimeRate())
                 .append("분 ")
                 .append(detailPath.getDriveRoute().getParkingInfo().getRates())
                 .append("원, ")
                 .append("잔여(")
                 .append(detailPath.getDriveRoute().getParkingInfo().getCapacity() - detailPath.getDriveRoute()
                                                                                               .getParkingInfo()
                                                                                               .getCurParking())
                 .append(")면").toString();
    });

    TrafficType(Integer trafficTypeNum,
                String trafficTypeName,
                String startStr,
                String endStr,
                Function<RouteResponse.Path.Detail.DetailPath, String> startExpression,
                Function<RouteResponse.Path.Detail.DetailPath, String> endExpression) {
        this.trafficTypeNum = trafficTypeNum;
        this.trafficTypeName = trafficTypeName;
        this.startStr = startStr;
        this.endStr = endStr;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
    }

    private Integer trafficTypeNum;
    private String trafficTypeName;
    private String startStr;
    private String endStr;
    private Function<RouteResponse.Path.Detail.DetailPath, String> startExpression;
    private Function<RouteResponse.Path.Detail.DetailPath, String> endExpression;

    public void setTrafficTypeNum(Integer trafficTypeNum) {
        this.trafficTypeNum = trafficTypeNum;
    }

    public void setTrafficTypeName(String trafficTypeName) {
        this.trafficTypeName = trafficTypeName;
    }

    public void setStartStr(String startStr) {
        this.startStr = startStr;
    }

    public void setEndStr(String endStr) {
        this.endStr = endStr;
    }

    public Function<RouteResponse.Path.Detail.DetailPath, String> getStartExpression() {
        return startExpression;
    }

    public void setStartExpression(Function<RouteResponse.Path.Detail.DetailPath, String> startExpression) {
        this.startExpression = startExpression;
    }

    public Function<RouteResponse.Path.Detail.DetailPath, String> getEndExpression() {
        return endExpression;
    }

    public void setEndExpression(Function<RouteResponse.Path.Detail.DetailPath, String> endExpression) {
        this.endExpression = endExpression;
    }

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

    public static String getStartSubName(RouteResponse.Path.Detail.DetailPath detailPath) {
        TrafficType trafficType = Arrays.stream(TrafficType.values())
                                        .filter(x -> x.getTrafficTypeName().equals(detailPath.getTrafficType()))
                                        .findFirst()
                                        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 대중교통 유형 입니다."));
        return trafficType.startExpression.apply(detailPath);
    }

    public static String getEndSubName(RouteResponse.Path.Detail.DetailPath detailPath) {
        TrafficType trafficType = Arrays.stream(TrafficType.values())
                                        .filter(x -> x.getTrafficTypeName().equals(detailPath.getTrafficType()))
                                        .findFirst()
                                        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 대중교통 유형 입니다."));
        return trafficType.endExpression.apply(detailPath);
    }
}
