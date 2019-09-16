package com.bamplee.chomi.api.application;

import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsDirectionDrivingResponse;
import com.bamplee.chomi.api.datatool.odsay.dto.OdSaySearchPubTransPathResponse;
import com.bamplee.chomi.api.datatool.odsay.dto.OdSaySearchPubTransPathResponse.Result.Info;
import com.bamplee.chomi.api.datatool.odsay.dto.OdSaySearchPubTransPathResponse.Result.Path.SubPath;
import com.bamplee.chomi.api.datatool.openweathermap.dto.ForecastResponse;
import com.bamplee.chomi.api.datatool.seoul.dto.ForecastWarningResponse;
import com.bamplee.chomi.api.infrastructure.persistence.jpa.entity.BikeParkingInfo;
import com.bamplee.chomi.api.infrastructure.persistence.jpa.entity.ParkingInfo;

import java.util.List;

public class RouteResponse {
    private ForecastWarningResponse.Data.Row forecastWarning;
    private ForecastResponse.Item forecast;
    private NaverMapsDirectionDrivingResponse driveRoute;
    private List<Path> pathList;

    public ForecastWarningResponse.Data.Row getForecastWarning() {
        return forecastWarning;
    }

    public void setForecastWarning(ForecastWarningResponse.Data.Row forecastWarning) {
        this.forecastWarning = forecastWarning;
    }

    public ForecastResponse.Item getForecast() {
        return forecast;
    }

    public void setForecast(ForecastResponse.Item forecast) {
        this.forecast = forecast;
    }

    public NaverMapsDirectionDrivingResponse getDriveRoute() {
        return driveRoute;
    }

    public void setDriveRoute(NaverMapsDirectionDrivingResponse driveRoute) {
        this.driveRoute = driveRoute;
    }

    public List<Path> getPathList() {
        return pathList;
    }

    public void setPathList(List<Path> pathList) {
        this.pathList = pathList;
    }

    public static class Path {
        private Boolean useBus;
        private Boolean useSubway;
        private Boolean useBike;
        private Boolean useCar;
        private Integer pathType;
        private Summary summary;
        private Detail detail;
        private List<SubPathInfo> subPathList;
        private Info info;

        public Summary getSummary() {
            return summary;
        }

        public void setSummary(Summary summary) {
            this.summary = summary;
        }

        public Detail getDetail() {
            return detail;
        }

        public void setDetail(Detail detail) {
            this.detail = detail;
        }

        public Boolean getUseBus() {
            return useBus;
        }

        public void setUseBus(Boolean useBus) {
            this.useBus = useBus;
        }

        public Boolean getUseSubway() {
            return useSubway;
        }

        public void setUseSubway(Boolean useSubway) {
            this.useSubway = useSubway;
        }

        public Boolean getUseBike() {
            return useBike;
        }

        public void setUseBike(Boolean useBike) {
            this.useBike = useBike;
        }

        public Boolean getUseCar() {
            return useCar;
        }

        public void setUseCar(Boolean useCar) {
            this.useCar = useCar;
        }

        public Integer getPathType() {
            return pathType;
        }

        public void setPathType(Integer pathType) {
            this.pathType = pathType;
        }

        public List<SubPathInfo> getSubPathList() {
            return subPathList;
        }

        public void setSubPathList(List<SubPathInfo> subPathList) {
            this.subPathList = subPathList;
        }

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public static class Summary {
            //총 걸린 시간
            private Integer totalTime;
            // 총 금액
            private Integer totalPrice;
            // 경유 주차장 명
            private String parkingName;
            // 주차 면(주차 가능 차량 수)
            private Integer capacity;
            // 현재 주차중인 대수
            private Integer curParking;
            // 유무료구분명
            private String payNm;
            // 추가 단위 요금
            private Integer addRates;
            // 추가 단위 시간(분 단위)
            private Integer addTimeRate;
            // 시간 상태 바 정보
            private List<TimeBar> timeBarList;

            public Integer getTotalTime() {
                return totalTime;
            }

            public void setTotalTime(Integer totalTime) {
                this.totalTime = totalTime;
            }

            public Integer getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(Integer totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getParkingName() {
                return parkingName;
            }

            public void setParkingName(String parkingName) {
                this.parkingName = parkingName;
            }

            public Integer getCapacity() {
                return capacity;
            }

            public void setCapacity(Integer capacity) {
                this.capacity = capacity;
            }

            public Integer getCurParking() {
                return curParking;
            }

            public void setCurParking(Integer curParking) {
                this.curParking = curParking;
            }

            public String getPayNm() {
                return payNm;
            }

            public void setPayNm(String payNm) {
                this.payNm = payNm;
            }

            public Integer getAddRates() {
                return addRates;
            }

            public void setAddRates(Integer addRates) {
                this.addRates = addRates;
            }

            public Integer getAddTimeRate() {
                return addTimeRate;
            }

            public void setAddTimeRate(Integer addTimeRate) {
                this.addTimeRate = addTimeRate;
            }

            public List<TimeBar> getTimeBarList() {
                return timeBarList;
            }

            public void setTimeBarList(List<TimeBar> timeBarList) {
                this.timeBarList = timeBarList;
            }

            public static class TimeBar {
                private String trafficType;
                private Integer length;
                private Integer time;

                public String getTrafficType() {
                    return trafficType;
                }

                public void setTrafficType(String trafficType) {
                    this.trafficType = trafficType;
                }

                public Integer getLength() {
                    return length;
                }

                public void setLength(Integer length) {
                    this.length = length;
                }

                public Integer getTime() {
                    return time;
                }

                public void setTime(Integer time) {
                    this.time = time;
                }
            }
        }

        public static class Detail {
            private List<DetailPath> detailPathList;
            private List<SubPathInfo> subPathList;
            private Info info;
            // 경유 주차장 정보
            private ParkingInfo parkingInfo;

            public List<SubPathInfo> getSubPathList() {
                return subPathList;
            }

            public void setSubPathList(List<SubPathInfo> subPathList) {
                this.subPathList = subPathList;
            }

            public Info getInfo() {
                return info;
            }

            public void setInfo(Info info) {
                this.info = info;
            }

            public ParkingInfo getParkingInfo() {
                return parkingInfo;
            }

            public void setParkingInfo(ParkingInfo parkingInfo) {
                this.parkingInfo = parkingInfo;
            }

            public List<DetailPath> getDetailPathList() {
                return detailPathList;
            }

            public void setDetailPathList(List<DetailPath> detailPathList) {
                this.detailPathList = detailPathList;
            }

            public static class DetailPath {
                // 이동 수단 종류 (도보, 버스, 지하철), 1-지하철, 2-버스, 3-도보
                private String trafficType;
                // 이동 거리
                private Integer distance;
                // 이동 소요 시간
                private Integer sectionTime;
                // 교통 수단 정보 확장 노드
                private List<OdSaySearchPubTransPathResponse.Result.Path.SubPath.Lane> lane;
                // 이동 역 수
                private Integer stationCount;
                // 경로 상세구간 정보 확장 노드
                private OdSaySearchPubTransPathResponse.Result.Path.SubPath.PassStopList passStopList;
                // 경로 상세구간 정보 확장 노드(자동차인 경우)
                private DriveRoute driveRoute;
                private Integer fuelPrice;
                // 승차 정류장/역 X 좌표
                private Double startX;
                // 승차 정류장/역 Y 좌표
                private Double startY;
                // 승차 역명
                private String startName;
                // 도착역 X 좌표
                private Double endX;
                // 도착역 Y 좌표
                private Double endY;
                // 도착역 id
                // 도착역명
                private String endName;

                public DetailPath() {
                }

                public String getTrafficType() {
                    return trafficType;
                }

                public void setTrafficType(String trafficType) {
                    this.trafficType = trafficType;
                }

                public Integer getDistance() {
                    return distance;
                }

                public void setDistance(Integer distance) {
                    this.distance = distance;
                }

                public Integer getSectionTime() {
                    return sectionTime;
                }

                public void setSectionTime(Integer sectionTime) {
                    this.sectionTime = sectionTime;
                }

                public List<SubPath.Lane> getLane() {
                    return lane;
                }

                public void setLane(List<SubPath.Lane> lane) {
                    this.lane = lane;
                }

                public Integer getStationCount() {
                    return stationCount;
                }

                public void setStationCount(Integer stationCount) {
                    this.stationCount = stationCount;
                }

                public SubPath.PassStopList getPassStopList() {
                    return passStopList;
                }

                public void setPassStopList(SubPath.PassStopList passStopList) {
                    this.passStopList = passStopList;
                }

                public DriveRoute getDriveRoute() {
                    return driveRoute;
                }

                public void setDriveRoute(DriveRoute driveRoute) {
                    this.driveRoute = driveRoute;
                }

                public Integer getFuelPrice() {
                    return fuelPrice;
                }

                public void setFuelPrice(Integer fuelPrice) {
                    this.fuelPrice = fuelPrice;
                }

                public Double getStartX() {
                    return startX;
                }

                public void setStartX(Double startX) {
                    this.startX = startX;
                }

                public Double getStartY() {
                    return startY;
                }

                public void setStartY(Double startY) {
                    this.startY = startY;
                }

                public String getStartName() {
                    return startName;
                }

                public void setStartName(String startName) {
                    this.startName = startName;
                }

                public Double getEndX() {
                    return endX;
                }

                public void setEndX(Double endX) {
                    this.endX = endX;
                }

                public Double getEndY() {
                    return endY;
                }

                public void setEndY(Double endY) {
                    this.endY = endY;
                }

                public String getEndName() {
                    return endName;
                }

                public void setEndName(String endName) {
                    this.endName = endName;
                }

                public static class DriveRoute {
                    private List<Double[]> path;
                    private List<NaverMapsDirectionDrivingResponse.Route.Guide> guide;

                    public List<Double[]> getPath() {
                        return path;
                    }

                    public void setPath(List<Double[]> path) {
                        this.path = path;
                    }

                    public List<NaverMapsDirectionDrivingResponse.Route.Guide> getGuide() {
                        return guide;
                    }

                    public void setGuide(List<NaverMapsDirectionDrivingResponse.Route.Guide> guide) {
                        this.guide = guide;
                    }
                }
            }
        }

        public static class SubPathInfo {
            private SubPath subPath;
            private ParkingRouteInfo parkingRouteInfo;
            private BikeParkingRouteInfo bikeParkingRouteInfo;

            public SubPath getSubPath() {
                return subPath;
            }

            public void setSubPath(SubPath subPath) {
                this.subPath = subPath;
            }

            public ParkingRouteInfo getParkingRouteInfo() {
                return parkingRouteInfo;
            }

            public void setParkingRouteInfo(ParkingRouteInfo parkingRouteInfo) {
                this.parkingRouteInfo = parkingRouteInfo;
            }

            public BikeParkingRouteInfo getBikeParkingRouteInfo() {
                return bikeParkingRouteInfo;
            }

            public void setBikeParkingRouteInfo(BikeParkingRouteInfo bikeParkingRouteInfo) {
                this.bikeParkingRouteInfo = bikeParkingRouteInfo;
            }

            public static class ParkingRouteInfo {
                private Integer totalTime;
                private NaverMapsDirectionDrivingResponse subPathRoute;
                private ParkingInfo parkingInfo;

                public Integer getTotalTime() {
                    return totalTime;
                }

                public void setTotalTime(Integer totalTime) {
                    this.totalTime = totalTime;
                }

                public NaverMapsDirectionDrivingResponse getSubPathRoute() {
                    return subPathRoute;
                }

                public void setSubPathRoute(NaverMapsDirectionDrivingResponse subPathRoute) {
                    this.subPathRoute = subPathRoute;
                }

                public ParkingInfo getParkingInfo() {
                    return parkingInfo;
                }

                public void setParkingInfo(ParkingInfo parkingInfo) {
                    this.parkingInfo = parkingInfo;
                }
            }

            public static class DriveRoute {
                private List<Double[]> path;
                private List<NaverMapsDirectionDrivingResponse.Route.Guide> guide;

                public List<Double[]> getPath() {
                    return path;
                }

                public void setPath(List<Double[]> path) {
                    this.path = path;
                }

                public List<NaverMapsDirectionDrivingResponse.Route.Guide> getGuide() {
                    return guide;
                }

                public void setGuide(List<NaverMapsDirectionDrivingResponse.Route.Guide> guide) {
                    this.guide = guide;
                }
            }

            public static class BikeParkingRouteInfo {
                private Integer totalTime;
                private NaverMapsDirectionDrivingResponse subPathRoute;
                private BikeParkingInfo startBikeParkingInfo;
                private BikeParkingInfo endBikeParkingInfo;

                public Integer getTotalTime() {
                    return totalTime;
                }

                public void setTotalTime(Integer totalTime) {
                    this.totalTime = totalTime;
                }

                public NaverMapsDirectionDrivingResponse getSubPathRoute() {
                    return subPathRoute;
                }

                public void setSubPathRoute(NaverMapsDirectionDrivingResponse subPathRoute) {
                    this.subPathRoute = subPathRoute;
                }

                public BikeParkingInfo getStartBikeParkingInfo() {
                    return startBikeParkingInfo;
                }

                public void setStartBikeParkingInfo(BikeParkingInfo startBikeParkingInfo) {
                    this.startBikeParkingInfo = startBikeParkingInfo;
                }

                public BikeParkingInfo getEndBikeParkingInfo() {
                    return endBikeParkingInfo;
                }

                public void setEndBikeParkingInfo(BikeParkingInfo endBikeParkingInfo) {
                    this.endBikeParkingInfo = endBikeParkingInfo;
                }
            }
        }
    }
}
