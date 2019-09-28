package com.bamplee.chomi.api.interfaces.place.dto.response;

import com.bamplee.chomi.api.application.RouteResponse;
import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsDirectionDrivingResponse;
import com.bamplee.chomi.api.datatool.odsay.dto.OdSaySearchPubTransPathResponse;
import com.bamplee.chomi.api.datatool.openweathermap.dto.ForecastResponse;
import com.bamplee.chomi.api.datatool.seoul.dto.ForecastWarningResponse;
import com.bamplee.chomi.api.infrastructure.persistence.jpa.entity.BikeParkingInfo;
import com.bamplee.chomi.api.infrastructure.persistence.jpa.entity.ParkingInfo;

import java.util.List;

public class V2RouteResponse {
    private DriveRoute driveRoute;
    private List<Path> pathList;

    public DriveRoute getDriveRoute() {
        return driveRoute;
    }

    public void setDriveRoute(DriveRoute driveRoute) {
        this.driveRoute = driveRoute;
    }

    public List<Path> getPathList() {
        return pathList;
    }

    public void setPathList(List<Path> pathList) {
        this.pathList = pathList;
    }

    public static class DriveRoute {
        private NaverMapsDirectionDrivingResponse.Route.Summary.Start start;
        private NaverMapsDirectionDrivingResponse.Route.Summary.Goal goal;
        private Integer distance;
        private Integer duration;
        private Integer tollFare;
        private Integer fuelPrice;
        private List<Double[]> path;
        private List<Guide> guide;

        public NaverMapsDirectionDrivingResponse.Route.Summary.Start getStart() {
            return start;
        }

        public void setStart(NaverMapsDirectionDrivingResponse.Route.Summary.Start start) {
            this.start = start;
        }

        public NaverMapsDirectionDrivingResponse.Route.Summary.Goal getGoal() {
            return goal;
        }

        public void setGoal(NaverMapsDirectionDrivingResponse.Route.Summary.Goal goal) {
            this.goal = goal;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public Integer getTollFare() {
            return tollFare;
        }

        public void setTollFare(Integer tollFare) {
            this.tollFare = tollFare;
        }

        public Integer getFuelPrice() {
            return fuelPrice;
        }

        public void setFuelPrice(Integer fuelPrice) {
            this.fuelPrice = fuelPrice;
        }

        public List<Double[]> getPath() {
            return path;
        }

        public void setPath(List<Double[]> path) {
            this.path = path;
        }

        public List<Guide> getGuide() {
            return guide;
        }

        public void setGuide(List<Guide> guide) {
            this.guide = guide;
        }

        public static class Guide {
            private String instructions;
            private Integer distance;
            private Integer duration;

            public String getInstructions() {
                return instructions;
            }

            public void setInstructions(String instructions) {
                this.instructions = instructions;
            }

            public Integer getDistance() {
                return distance;
            }

            public void setDistance(Integer distance) {
                this.distance = distance;
            }

            public Integer getDuration() {
                return duration;
            }

            public void setDuration(Integer duration) {
                this.duration = duration;
            }
        }
    }

    public static class Path {
        private Summary summary;
        private Detail detail;
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

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public static class Info {
            // 요금
            private Integer payment;
            // 버스 환승 카운트
            private Integer busTransitCount;
            // 지하철 환승 카운트
            private Integer subwayTransitCount;
            // 버스 정류장 합
            private Integer busStationCount;
            // 지하철 정류장 합
            private Integer subwayStationCount;
            private Integer totalStationCount;
            // 총 소요시간
            private Integer totalTime;
            // 총 도보 이동 거리
            private Integer totalWalk;
            // 도보를 제외한 총 이동 거리
            private Integer trafficDistance;
            // 총 거리
            private Integer totalDistance;
            // 총 도보 시간
            private Integer totalWalkTime;

            public Integer getPayment() {
                return payment;
            }

            public void setPayment(Integer payment) {
                this.payment = payment;
            }

            public Integer getBusTransitCount() {
                return busTransitCount;
            }

            public void setBusTransitCount(Integer busTransitCount) {
                this.busTransitCount = busTransitCount;
            }

            public Integer getSubwayTransitCount() {
                return subwayTransitCount;
            }

            public void setSubwayTransitCount(Integer subwayTransitCount) {
                this.subwayTransitCount = subwayTransitCount;
            }

            public Integer getBusStationCount() {
                return busStationCount;
            }

            public void setBusStationCount(Integer busStationCount) {
                this.busStationCount = busStationCount;
            }

            public Integer getSubwayStationCount() {
                return subwayStationCount;
            }

            public void setSubwayStationCount(Integer subwayStationCount) {
                this.subwayStationCount = subwayStationCount;
            }

            public Integer getTotalStationCount() {
                return totalStationCount;
            }

            public void setTotalStationCount(Integer totalStationCount) {
                this.totalStationCount = totalStationCount;
            }

            public Integer getTotalTime() {
                return totalTime;
            }

            public void setTotalTime(Integer totalTime) {
                this.totalTime = totalTime;
            }

            public Integer getTotalWalk() {
                return totalWalk;
            }

            public void setTotalWalk(Integer totalWalk) {
                this.totalWalk = totalWalk;
            }

            public Integer getTrafficDistance() {
                return trafficDistance;
            }

            public void setTrafficDistance(Integer trafficDistance) {
                this.trafficDistance = trafficDistance;
            }

            public Integer getTotalDistance() {
                return totalDistance;
            }

            public void setTotalDistance(Integer totalDistance) {
                this.totalDistance = totalDistance;
            }

            public Integer getTotalWalkTime() {
                return totalWalkTime;
            }

            public void setTotalWalkTime(Integer totalWalkTime) {
                this.totalWalkTime = totalWalkTime;
            }
        }

        public static class Summary {
            // 차로 이동 시 걸리는 시간
            private Integer driveTime;
            // 차로 이동 시 가격(유류비)
            private Integer drivePrice;
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
            // 주차장 종류
            private String parkingType;
            // 추가 단위 요금
            private Integer addRates;
            // 추가 단위 시간(분 단위)
            private Integer addTimeRate;
            // 시간 상태 바 정보
            private List<TimeBar> timeBarList;

            public Integer getDriveTime() {
                return driveTime;
            }

            public void setDriveTime(Integer driveTime) {
                this.driveTime = driveTime;
            }

            public Integer getDrivePrice() {
                return drivePrice;
            }

            public void setDrivePrice(Integer drivePrice) {
                this.drivePrice = drivePrice;
            }

            public String getParkingType() {
                return parkingType;
            }

            public void setParkingType(String parkingType) {
                this.parkingType = parkingType;
            }

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
            // 경유 주차장 정보
            private ParkingInfo parkingInfo;

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
                private String startSubName;
                // 도착역 X 좌표
                private Double endX;
                // 도착역 Y 좌표
                private Double endY;
                // 도착역 id
                // 도착역명
                private String endName;
                private String endSubName;

                public String getStartSubName() {
                    return startSubName;
                }

                public void setStartSubName(String startSubName) {
                    this.startSubName = startSubName;
                }

                public String getEndSubName() {
                    return endSubName;
                }

                public void setEndSubName(String endSubName) {
                    this.endSubName = endSubName;
                }

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

                public List<OdSaySearchPubTransPathResponse.Result.Path.SubPath.Lane> getLane() {
                    return lane;
                }

                public void setLane(List<OdSaySearchPubTransPathResponse.Result.Path.SubPath.Lane> lane) {
                    this.lane = lane;
                }

                public Integer getStationCount() {
                    return stationCount;
                }

                public void setStationCount(Integer stationCount) {
                    this.stationCount = stationCount;
                }

                public OdSaySearchPubTransPathResponse.Result.Path.SubPath.PassStopList getPassStopList() {
                    return passStopList;
                }

                public void setPassStopList(OdSaySearchPubTransPathResponse.Result.Path.SubPath.PassStopList passStopList) {
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
                    private List<V2RouteResponse.DriveRoute.Guide> guide;

                    public List<Double[]> getPath() {
                        return path;
                    }

                    public void setPath(List<Double[]> path) {
                        this.path = path;
                    }

                    public List<V2RouteResponse.DriveRoute.Guide> getGuide() {
                        return guide;
                    }

                    public void setGuide(List<V2RouteResponse.DriveRoute.Guide> guide) {
                        this.guide = guide;
                    }
                }
            }
        }

        public static class SubPathInfo {
            private OdSaySearchPubTransPathResponse.Result.Path.SubPath subPath;
            private ParkingRouteInfo parkingRouteInfo;

            public OdSaySearchPubTransPathResponse.Result.Path.SubPath getSubPath() {
                return subPath;
            }

            public void setSubPath(OdSaySearchPubTransPathResponse.Result.Path.SubPath subPath) {
                this.subPath = subPath;
            }

            public ParkingRouteInfo getParkingRouteInfo() {
                return parkingRouteInfo;
            }

            public void setParkingRouteInfo(ParkingRouteInfo parkingRouteInfo) {
                this.parkingRouteInfo = parkingRouteInfo;
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
        }
    }
}
