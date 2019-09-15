package com.bamplee.chomi.api.application;

import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsDirectionDrivingResponse;
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
