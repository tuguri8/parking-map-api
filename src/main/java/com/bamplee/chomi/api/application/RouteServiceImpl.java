package com.bamplee.chomi.api.application;

import com.bamplee.chomi.api.application.RouteResponse.Path.SubPathInfo;
import com.bamplee.chomi.api.application.RouteResponse.Path.SubPathInfo.ParkingRouteInfo;
import com.bamplee.chomi.api.datatool.naver.NaverMapsClient;
import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsDirectionDrivingResponse;
import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsGcResponse;
import com.bamplee.chomi.api.datatool.odsay.OdSayClient;
import com.bamplee.chomi.api.datatool.odsay.dto.OdSaySearchPubTransPathResponse;
import com.bamplee.chomi.api.datatool.odsay.dto.OdSaySearchPubTransPathResponse.Result.Path;
import com.bamplee.chomi.api.datatool.odsay.dto.OdSaySearchPubTransPathResponse.Result.Path.SubPath;
import com.bamplee.chomi.api.datatool.openweathermap.OpenWeatherMapClient;
import com.bamplee.chomi.api.datatool.seoul.SeoulOpenApiClient;
import com.bamplee.chomi.api.infrastructure.persistence.jpa.entity.BikeParkingInfo;
import com.bamplee.chomi.api.infrastructure.persistence.jpa.entity.ParkingInfo;
import com.bamplee.chomi.api.interfaces.place.dto.response.V2RouteResponse;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bamplee.chomi.api.application.RouteResponse.Path.SubPathInfo.*;

@Service
public class RouteServiceImpl implements RouteService {
    @Value("${odsay.key}")
    String apiKey;
    @Value("${openweathermap.key}")
    String openWeatherApiKey;
    @Value("${seoul-openapi.key}")
    String seoulOpenApiKey;

    private static final Logger log = LoggerFactory.getLogger(RouteServiceImpl.class);
    private final String PARKING_DETAIL_URL = "http://52.78.160.114/?code=";
    private final ParkingSyncService parkingSyncService;
    private final BikeParkingSyncService bikeParkingSyncService;
    private final NaverMapsClient naverMapsClient;
    private final OdSayClient odSayClient;
    private final OpenWeatherMapClient openWeatherMapClient;
    private final SeoulOpenApiClient seoulOpenApiClient;
    private final ModelMapper modelMapper;

    public RouteServiceImpl(ParkingSyncService parkingSyncService,
                            BikeParkingSyncService bikeParkingSyncService,
                            NaverMapsClient naverMapsClient,
                            OdSayClient odSayClient,
                            OpenWeatherMapClient openWeatherMapClient,
                            SeoulOpenApiClient seoulOpenApiClient, ModelMapper modelMapper) {
        this.parkingSyncService = parkingSyncService;
        this.bikeParkingSyncService = bikeParkingSyncService;
        this.naverMapsClient = naverMapsClient;
        this.odSayClient = odSayClient;
        this.openWeatherMapClient = openWeatherMapClient;
        this.seoulOpenApiClient = seoulOpenApiClient;
        this.modelMapper = modelMapper;
    }

    @Override
    public V2RouteResponse route(String departureX,
                                 String departureY,
                                 String destinationX,
                                 String destinationY,
                                 String sortType,
                                 String parkType) {
        // 대중교통 경로부터 찾기
        OdSaySearchPubTransPathResponse searchPubTransPath = this.getSearchPubTransPath(departureX,
                                                                                        departureY,
                                                                                        destinationX,
                                                                                        destinationY);
        List<RouteResponse.Path> pathList = Lists.newArrayList();
        for (Path p : searchPubTransPath.getResult()
                                        .getPathList()) {
            // 결과 종류, 1-지하철, 2-버스, 3-버스+지하철
/*
            if(p.getPathType() == 1) {
                useSubway = true;
            }
            else if(p.getPathType() == 2) {
                useBus = true;
            }
            else if(p.getPathType() == 3) {
                useSubway = true;
                useBus = true;
            }
*/
            List<SubPathInfo> tempList = Lists.newArrayList();
            RouteResponse.Path routeResponse = new RouteResponse.Path();
            routeResponse.setInfo(p.getInfo());
            routeResponse.setPathType(p.getPathType());
            List<SubPath> subPathList = p.getSubPathList();
            for (SubPath subPath : subPathList) {
                SubPathInfo subPathInfo = new SubPathInfo();
                subPathInfo.setSubPath(subPath);
                tempList.add(subPathInfo);
            }
            routeResponse.setSubPathList(tempList);
            pathList.add(routeResponse);
        }

        List<RouteResponse.Path> parkingRouteList = pathList.stream().map(x -> {
            Boolean isChecked = false;
            RouteResponse.Path path = new RouteResponse.Path();
            List<SubPathInfo> tempList = Lists.newArrayList();
            for (SubPathInfo originSubPathInfo : x.getSubPathList()) {
                SubPathInfo subPathInfo = new SubPathInfo();
                SubPath originSubPath = originSubPathInfo.getSubPath();
                if (originSubPath.getTrafficType() != 3 && !isChecked && !originSubPath.getStartX()
                                                                                       .equals(originSubPath.getEndX()) && !originSubPath.getStartY()
                                                                                                                                         .equals(
                                                                                                                                             originSubPath
                                                                                                                                                 .getEndY())) {
                    Optional<ParkingRouteInfo> parkingRouteInfo = this.getParkingRouteInfo(Double.valueOf(departureX),
                                                                                           Double.valueOf(departureY),
                                                                                           originSubPath.getEndX(),
                                                                                           originSubPath.getEndY());
                    if (parkingRouteInfo.isPresent()) {
                        subPathInfo.setParkingRouteInfo(parkingRouteInfo.get());
                        isChecked = true;
                        tempList = Lists.newArrayList();
                    }
                }
                subPathInfo.setSubPath(originSubPath);
                tempList.add(subPathInfo);
            }
            if (isChecked) {
                Integer duration = tempList.stream().map(y -> {
                    if (y.getParkingRouteInfo() == null) {
                        return y.getSubPath().getSectionTime();
                    } else {
                        if (y.getParkingRouteInfo()
                             .getSubPathRoute().getCode() == 1) {
                            return 0;
                        }
                        Integer sum = (y.getParkingRouteInfo()
                                        .getSubPathRoute()
                                        .getRoute()
                                        .getOrDefault("traoptimal", Lists.newArrayList())
                                        .get(0).getSummary().getDuration() / 1000) / 60;
                        y.getParkingRouteInfo().setTotalTime(sum);
                        return sum;
                    }
                }).reduce((a, b) -> a + b).orElse(0);
                OdSaySearchPubTransPathResponse.Result.Info info = new OdSaySearchPubTransPathResponse.Result.Info();
                info.setTotalStationCount(x.getInfo().getTotalStationCount());
                info.setBusStationCount(x.getInfo().getBusStationCount());
                info.setBusTransitCount(x.getInfo().getBusTransitCount());
                info.setSubwayStationCount(x.getInfo().getSubwayStationCount());
                info.setSubwayTransitCount(x.getInfo().getSubwayTransitCount());
                info.setTotalTime(duration);
                info.setMapObj(x.getInfo().getMapObj());
                info.setPayment(x.getInfo().getPayment());
                path.setUseBus(x.getUseBus());
                path.setUseSubway(x.getUseSubway());
                path.setInfo(info);
                path.setPathType(x.getPathType());
                path.setSubPathList(tempList.stream().peek(y -> {
                    if (y.getParkingRouteInfo() != null) {
                        y.setSubPath(null);
                    }
                }).collect(Collectors.toList()));
                return path;
            } else {
                return null;
            }
        }).collect(Collectors.toList());

        String start = departureX + "," + departureY;
        String goal = destinationX + "," + destinationY;
        NaverMapsDirectionDrivingResponse directionDrivingResponse = naverMapsClient.direction5Driving(start, goal, "t");

        pathList = Stream.concat(pathList.stream(), parkingRouteList.stream())
                         .filter(Objects::nonNull)
                         .peek(x -> {
                             x.setUseBus(x.getSubPathList().stream().anyMatch(y -> y.getSubPath() != null && y.getSubPath()
                                                                                                              .getTrafficType() == 2));
                             x.setUseSubway(x.getSubPathList().stream().anyMatch(y -> y.getSubPath() != null && y.getSubPath()
                                                                                                                 .getTrafficType() == 1));
                             x.setUseBike(x.getSubPathList().stream().anyMatch(y -> y.getBikeParkingRouteInfo() != null));
                             x.setUseCar(x.getSubPathList().stream().anyMatch(y -> y.getParkingRouteInfo() != null));
                         })
                         .filter(x -> !(!x.getUseBus() && !x.getUseSubway() && !x.getUseBike() && x.getUseCar()))
                         .filter(RouteResponse.Path::getUseCar)
                         .peek(x -> {
                             SubPathInfo subPathInfo = x.getSubPathList().stream()
                                                        .filter(y -> y.getParkingRouteInfo() != null)
                                                        .findFirst().get();
                             subPathInfo.getParkingRouteInfo()
                                        .getParkingInfo()
                                        .setParkingName(changeName(subPathInfo.getParkingRouteInfo()
                                                                              .getParkingInfo()
                                                                              .getParkingName()));
                             RouteResponse.Path.Summary summary = new RouteResponse.Path.Summary();
                             summary.setAddRates(subPathInfo.getParkingRouteInfo().getParkingInfo().getAddRates());
                             summary.setAddTimeRate(subPathInfo.getParkingRouteInfo().getParkingInfo().getAddTimeRate());
                             summary.setCapacity(subPathInfo.getParkingRouteInfo().getParkingInfo().getCapacity());
                             summary.setCurParking(subPathInfo.getParkingRouteInfo().getParkingInfo().getCurParking());
                             summary.setParkingName(subPathInfo.getParkingRouteInfo().getParkingInfo().getParkingName());
                             summary.setPayNm(subPathInfo.getParkingRouteInfo().getParkingInfo().getPayNm());
                             summary.setParkingType(subPathInfo.getParkingRouteInfo().getParkingInfo().getParkingType());

                             Double totalTime = Double.valueOf(x.getSubPathList().stream()
                                                                .map(path -> {
                                                                    if (path.getSubPath() != null) {
                                                                        return path.getSubPath().getSectionTime();
                                                                    }
                                                                    return path.getParkingRouteInfo().getTotalTime();
                                                                })
                                                                .reduce(0, Integer::sum));
                             List<RouteResponse.Path.Summary.TimeBar> timeBarList = x.getSubPathList().stream()
                                                                                     .map(this::calTimeBar)
                                                                                     .peek(timeBar -> {
                                                                                         Integer length =
                                                                                             (int) Math.ceil(timeBar.getTime() / totalTime * 21);
                                                                                         timeBar.setLength(length > 2 ? length : 2);
                                                                                     })
                                                                                     .collect(Collectors.toList());
                             RouteResponse.Path.Summary.TimeBar parkingTimeBar = new RouteResponse.Path.Summary.TimeBar();
                             parkingTimeBar.setLength(2);
                             parkingTimeBar.setTrafficType("PARKING");
                             parkingTimeBar.setTime(5);
                             timeBarList.add(1, parkingTimeBar);
                             summary.setTimeBarList(timeBarList);
                             summary.setTotalPrice(x.getInfo().getPayment());
                             summary.setTotalTime(x.getInfo().getTotalTime());
                             summary.setDriveTime(millToMinute(directionDrivingResponse.getRoute()
                                                                                       .get("traoptimal")
                                                                                       .get(0)
                                                                                       .getSummary()
                                                                                       .getDuration()));
                             summary.setDrivePrice(directionDrivingResponse.getRoute()
                                                                           .get("traoptimal")
                                                                           .get(0)
                                                                           .getSummary()
                                                                           .getFuelPrice() + directionDrivingResponse.getRoute()
                                                                                                                     .get("traoptimal")
                                                                                                                     .get(0)
                                                                                                                     .getSummary()
                                                                                                                     .getTollFare());

                             RouteResponse.Path.Detail detail = new RouteResponse.Path.Detail();
                             detail.setParkingInfo(subPathInfo.getParkingRouteInfo().getParkingInfo());
                             detail.setSubPathList(x.getSubPathList());
                             List<RouteResponse.Path.Detail.DetailPath> detailPathList = x.getSubPathList()
                                                                                          .stream()
                                                                                          .map(y -> transform(y))
                                                                                          .collect(Collectors.toList());
                             for (int i = 0; i < detailPathList.size(); i++) {
                                 if (detailPathList.get(i).getEndName() == null) {
                                     if (i == 0) {
                                         detailPathList.get(i).setEndName(summary.getParkingName());
                                     } else if (i == detailPathList.size() - 1) {
                                         detailPathList.get(i).setEndName("목적지 도착");
                                     } else {
                                         detailPathList.get(i).setEndName(detailPathList.get(i + 1).getStartName());
                                     }
                                 }
                                 if (detailPathList.get(i).getStartName() == null) {
                                     if (i == 0) {
                                         detailPathList.get(i).setStartName("승차 후 출발");
                                     } else {
                                         detailPathList.get(i).setStartName(detailPathList.get(i - 1).getEndName());
                                     }
                                 }

                             }
                             detailPathList = detailPathList.stream()
                                                            .peek(y -> {
                                                                StringBuffer startNameBuffer = new StringBuffer(y.getStartName());
                                                                StringBuffer endNameBuffer = new StringBuffer(y.getEndName());
                                                                y.setStartSubName(TrafficType.getStartSubName(y));
                                                                y.setEndSubName(TrafficType.getEndSubName(y));
                                                                y.setStartName(startNameBuffer.append(TrafficType.getTrafficTypeByName(y.getTrafficType()).getStartStr()).toString());
                                                                if(!y.getEndName().equals("목적지 도착")) y.setEndName(endNameBuffer.append(TrafficType.getTrafficTypeByName(y.getTrafficType()).getEndStr()).toString());
                                                            })
                                                            .collect(Collectors.toList());
                             x.getInfo().setTotalWalkTime(timeBarList.stream()
                                                                     .filter(timeBar -> timeBar.getTrafficType().equals("WALK"))
                                                                     .map(RouteResponse.Path.Summary.TimeBar::getTime)
                                                                     .reduce(0, Integer::sum));
                             x.getInfo().setTotalWalk(detailPathList.stream()
                                                                    .filter(detailPath -> detailPath.getTrafficType().equals("WALK"))
                                                                    .map(RouteResponse.Path.Detail.DetailPath::getDistance)
                                                                    .reduce(0, Integer::sum));
                             x.getInfo().setTotalDistance(detailPathList.stream()
                                                                        .map(RouteResponse.Path.Detail.DetailPath::getDistance)
                                                                        .reduce(0, Integer::sum));
                             x.getInfo().setTrafficDistance(x.getInfo().getTotalDistance() - x.getInfo().getTotalWalk());
                             x.getInfo().setBusTransitCount((int) detailPathList.stream()
                                                                                .filter(detailPath -> detailPath.getTrafficType()
                                                                                                                .equals("BUS"))
                                                                                .count());
                             x.getInfo().setSubwayTransitCount((int) detailPathList.stream()
                                                                                   .filter(detailPath -> detailPath.getTrafficType()
                                                                                                                   .equals("SUBWAY"))
                                                                                   .count());
                             x.getInfo().setBusStationCount(detailPathList.stream()
                                                                          .filter(detailPath -> detailPath.getTrafficType().equals("BUS"))
                                                                          .map(RouteResponse.Path.Detail.DetailPath::getStationCount)
                                                                          .reduce(0, Integer::sum));
                             x.getInfo().setSubwayStationCount(detailPathList.stream()
                                                                             .filter(detailPath -> detailPath.getTrafficType()
                                                                                                             .equals("SUBWAY"))
                                                                             .map(RouteResponse.Path.Detail.DetailPath::getStationCount)
                                                                             .reduce(0, Integer::sum));
                             x.getInfo().setTotalStationCount(x.getInfo().getBusStationCount() + x.getInfo().getSubwayStationCount());
                             detail.setInfo(x.getInfo());
                             detail.setDetailPathList(detailPathList);
                             x.setSummary(summary);
                             x.setDetail(detail);
                         })
                         .collect(Collectors.toList());

        pathList = pathList.stream()
                           .filter(x -> {
                               if (parkType.equalsIgnoreCase("ALL")) {
                                   return true;
                               }
                               return x.getSummary().getParkingType().equalsIgnoreCase(parkType);
                           })
                           .sorted((a, b) -> {
                               if (sortType.equals("time")) {
                                   return a.getSummary().getTotalTime().compareTo(b.getSummary().getTotalTime());
                               } else if (sortType.equals("transport")) {
                                   return (a.getInfo().getBusTransitCount() + a.getInfo().getSubwayTransitCount()) - (b.getInfo()
                                                                                                                       .getBusTransitCount() + b
                                       .getInfo()
                                       .getSubwayTransitCount());
                               } else if (sortType.equals("price")) {
                                   return a.getSummary().getTotalPrice().compareTo(b.getSummary().getTotalPrice());
                               } else if (sortType.equals("walk")) {
                                   return a.getInfo().getTotalWalkTime().compareTo(b.getInfo().getTotalWalkTime());
                               }
                               return 0;
                           })
                           .collect(Collectors.toList());

//        ForecastResponse forecast = openWeatherMapClient.forecast(openWeatherApiKey, departureY, departureX);
        RouteResponse routeResponse = new RouteResponse();

        routeResponse.setPathList(pathList);
        routeResponse.setDriveRoute(transformDriveRoute(directionDrivingResponse));
//        Arrays.stream(seoulOpenApiClient.forecastWarningMinuteParticleOfDustService(seoulOpenApiKey, "1", "1000").getData().getRow())
//              .findFirst()
//              .ifPresent(
//                  routeResponse::setForecastWarning);
//        forecast.getList()
//                .stream()
//                .filter(x -> LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul"))
//                                          .isAfter(LocalDateTime.parse(x.getDtTxt(),
//                                                                       DateTimeFormatter.ofPattern(
//                                                                           "yyyy-MM-dd HH:mm:ss").withLocale(Locale.KOREA))
//                                                                .atZone(ZoneId.of("Asia/Seoul"))))
//                .min((a, b) -> b.getDt().compareTo(a.getDt()))
//                .ifPresent(routeResponse::setForecast);
//        if (routeResponse.getForecast() == null) {
//            forecast.getList().stream().min((a, b) -> b.getDt() - a.getDt()).ifPresent(routeResponse::setForecast);
//        }
        return modelMapper.map(routeResponse, V2RouteResponse.class);
    }

    private Optional<BikeParkingRouteInfo> getBikeParkingRouteInfo(Double startX, Double startY, Double endX, Double endY) {
        List<BikeParkingInfo> bikeParkingInfoList = bikeParkingSyncService.getBikeParkingInfoList();
        NaverMapsGcResponse startGeocode = this.getGeocode(startX, startY);
        NaverMapsGcResponse endGeocode = this.getGeocode(endX, endY);
        List<BikeParkingInfo> startBikeParkingInfoList = bikeParkingInfoList.stream()
                                                                            .filter(bikeParkingInfo -> this.isMatchDongName(bikeParkingInfo,
                                                                                                                            startGeocode.getResults()[0]
                                                                                                                                .getRegion()))
                                                                            .sorted(Comparator.comparing(x -> distance(
                                                                                startX,
                                                                                startY,
                                                                                x.getStationLongitude(),
                                                                                x.getStationLatitude())))
                                                                            .collect(Collectors.toList());
        List<BikeParkingInfo> endBikeParkingInfoList = bikeParkingInfoList.stream()
                                                                          .filter(bikeParkingInfo -> this.isMatchDongName(bikeParkingInfo,
                                                                                                                          endGeocode.getResults()[0]
                                                                                                                              .getRegion()))
                                                                          .sorted(Comparator.comparing(x -> distance(
                                                                              startX,
                                                                              startY,
                                                                              x.getStationLongitude(),
                                                                              x.getStationLatitude())))
                                                                          .collect(Collectors.toList());
        if (startBikeParkingInfoList.size() > 0 && endBikeParkingInfoList.size() > 0) {
            BikeParkingInfo startBikeParkingInfo = startBikeParkingInfoList.stream()
                                                                           .findFirst()
                                                                           .get();
            BikeParkingInfo endBikeParkingInfo = endBikeParkingInfoList.stream()
                                                                       .findFirst()
                                                                       .get();
            if (startBikeParkingInfo.getStationName().equals(endBikeParkingInfo.getStationName())) {
                return Optional.empty();
            }
            BikeParkingRouteInfo bikeParkingRouteInfo = new BikeParkingRouteInfo();
            bikeParkingRouteInfo.setStartBikeParkingInfo(startBikeParkingInfo);
            bikeParkingRouteInfo.setEndBikeParkingInfo(endBikeParkingInfo);
            bikeParkingRouteInfo.setSubPathRoute(this.getDirection5Driving(String.valueOf(startBikeParkingInfo.getStationLongitude()),
                                                                           String.valueOf(startBikeParkingInfo.getStationLatitude()),
                                                                           String.valueOf(endBikeParkingInfo.getStationLongitude()),
                                                                           String.valueOf(endBikeParkingInfo.getStationLatitude())));
            return Optional.of(bikeParkingRouteInfo);
        }
        return Optional.empty();
    }

    private Optional<ParkingRouteInfo> getParkingRouteInfo(Double startX, Double startY, Double endX, Double endY) {
        List<ParkingInfo> parkingInfoList = parkingSyncService.getParkingInfoList();
        NaverMapsGcResponse geocode = this.getGeocode(endX, endY);
        return parkingInfoList.stream()
                              .filter(parkingInfo -> this.isMatchDongName(parkingInfo,
                                                                          geocode.getResults()[0]
                                                                              .getRegion()))
                              .sorted(Comparator.comparing(x -> distance(
                                  endX,
                                  endY,
                                  x.getLng(),
                                  x.getLat())))
                              .map(x -> {
                                  ParkingRouteInfo parkingRouteInfo =
                                      new ParkingRouteInfo();
                                  parkingRouteInfo.setSubPathRoute(this.getDirection5Driving(
                                      String.valueOf(startX),
                                      String.valueOf(startY),
                                      String.valueOf(x.getLng()),
                                      String.valueOf(x.getLat())));
                                  parkingRouteInfo.setParkingInfo(x);
                                  return parkingRouteInfo;
                              })
                              .findFirst();
    }

    private List<ParkingInfo> transform(NaverMapsGcResponse.Result.Region region) {
        List<ParkingInfo> result = parkingSyncService.getParkingInfoList()
                                                     .stream()
                                                     .filter(parkingInfo -> this.isMatchDongName(parkingInfo, region))
                                                     .collect(Collectors.toList());
        return result;
    }

    private Boolean isMatchDongName(ParkingInfo parkingInfo, NaverMapsGcResponse.Result.Region region) {
        return parkingInfo.getSidoName()
                          .equals(region.getArea1()
                                        .getName())
            && parkingInfo.getGunguName()
                          .equals(region.getArea2()
                                        .getName())
            && parkingInfo.getDongName()
                          .equals(region.getArea3()
                                        .getName());
    }

    private Boolean isMatchDongName(BikeParkingInfo bikeParkingInfo, NaverMapsGcResponse.Result.Region region) {
        return bikeParkingInfo.getSidoName()
                              .equals(region.getArea1()
                                            .getName())
            && bikeParkingInfo.getGunguName()
                              .equals(region.getArea2()
                                            .getName())
            && bikeParkingInfo.getDongName()
                              .equals(region.getArea3()
                                            .getName());
    }

    private Boolean isMatchGunguName(ParkingInfo parkingInfo, NaverMapsGcResponse.Result.Region region) {
        return parkingInfo.getSidoName()
                          .equals(region.getArea1()
                                        .getName())
            && parkingInfo.getGunguName()
                          .equals(region.getArea2()
                                        .getName());
    }

    OdSaySearchPubTransPathResponse getSearchPubTransPath(String startX, String startY, String endX, String endY) {
        return odSayClient.searchPubTransPath(apiKey,
                                              startX,
                                              startY,
                                              endX,
                                              endY,
                                              "0",
                                              "0",
                                              "0");
    }

    private NaverMapsGcResponse getGeocode(Double lng, Double lat) {
        String coords = lng + "," + lat;
        return naverMapsClient.gc(coords,
                                  null,
                                  null,
                                  null,
                                  null,
                                  "json");
    }

    private NaverMapsDirectionDrivingResponse getDirection5Driving(String startX, String startY, String endX, String endY) {
        return naverMapsClient.direction5Driving(StringUtils.join(startX, ",", startY), StringUtils.join(endX, ",", endY), "t");
    }

    private static double distance(double lon1, double lat1, double lon2, double lat2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(
            deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 1.609344;
        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    private RouteResponse.Path.Summary.TimeBar calTimeBar(SubPathInfo subPathInfo) {
        RouteResponse.Path.Summary.TimeBar timeBar = new RouteResponse.Path.Summary.TimeBar();
        if (subPathInfo.getSubPath() == null) {
            timeBar.setLength(subPathInfo.getParkingRouteInfo().getTotalTime());
            timeBar.setTime(subPathInfo.getParkingRouteInfo().getTotalTime());
            timeBar.setTrafficType("CAR");
        } else {
            timeBar.setLength(subPathInfo.getSubPath().getSectionTime());
            timeBar.setTime(subPathInfo.getSubPath().getSectionTime());
            timeBar.setTrafficType(TrafficType.getTrafficTypeByNum(subPathInfo.getSubPath().getTrafficType()).getTrafficTypeName());
        }
        return timeBar;
    }

    private RouteResponse.Path.Detail.DetailPath transform(SubPathInfo subPathInfo) {
        RouteResponse.Path.Detail.DetailPath detailPath = new RouteResponse.Path.Detail.DetailPath();
        if (subPathInfo.getSubPath() != null) {
            String trafficTypeName = TrafficType.getTrafficTypeByNum(subPathInfo.getSubPath().getTrafficType()).getTrafficTypeName();
            String endName = subPathInfo.getSubPath().getEndName();
            String startName = subPathInfo.getSubPath().getStartName();
            detailPath.setTrafficType(trafficTypeName);
            detailPath.setDistance(subPathInfo.getSubPath().getDistance());
            detailPath.setSectionTime(subPathInfo.getSubPath().getSectionTime());
            detailPath.setEndName(endName != null ? changeName(endName, trafficTypeName) : null);
            detailPath.setStartName(startName != null ? changeName(startName, trafficTypeName) : null);
            detailPath.setLane(subPathInfo.getSubPath().getLane());
            detailPath.setPassStopList(subPathInfo.getSubPath().getPassStopList());
            detailPath.setStationCount(subPathInfo.getSubPath().getStationCount());
            detailPath.setStartX(subPathInfo.getSubPath().getStartX());
            detailPath.setStartY(subPathInfo.getSubPath().getStartY());
            detailPath.setEndX(subPathInfo.getSubPath().getEndX());
            detailPath.setEndY(subPathInfo.getSubPath().getEndY());
        } else {
            RouteResponse.Path.Detail.DetailPath.DriveRoute driveRoute = new RouteResponse.Path.Detail.DetailPath.DriveRoute();
            NaverMapsDirectionDrivingResponse.Route route = subPathInfo.getParkingRouteInfo()
                                                                       .getSubPathRoute()
                                                                       .getRoute()
                                                                       .get("traoptimal")
                                                                       .get(0);
            driveRoute.setPath(route.getPath());
            driveRoute.setGuide(route.getGuide());
            driveRoute.setParkingInfo(subPathInfo.getParkingRouteInfo().getParkingInfo());
            detailPath.setDriveRoute(driveRoute);
            detailPath.setDistance(route.getSummary().getDistance());
            detailPath.setSectionTime(millToMinute(route.getSummary().getDuration()));
            detailPath.setFuelPrice(route.getSummary().getFuelPrice());
            detailPath.setTrafficType("CAR");
            detailPath.setParkingDetailLink(PARKING_DETAIL_URL + subPathInfo.getParkingRouteInfo().getParkingInfo().getParkingCode());
        }
        return detailPath;
    }

    private RouteResponse.DriveRoute transformDriveRoute(NaverMapsDirectionDrivingResponse naverMapsDirectionDrivingResponse) {
        RouteResponse.DriveRoute driveRoute = new RouteResponse.DriveRoute();
        NaverMapsDirectionDrivingResponse.Route route = naverMapsDirectionDrivingResponse.getRoute()
                                                                                         .get("traoptimal")
                                                                                         .get(0);

        driveRoute.setPath(route.getPath());
        driveRoute.setGuide(route.getGuide()
                                 .stream()
                                 .peek(x -> {
                                     Integer min = millToMinute(x.getDuration());
                                     x.setDuration(min);
                                 })
                                 .collect(Collectors.toList()));
        driveRoute.setStart(route.getSummary().getStart());
        driveRoute.setGoal(route.getSummary().getGoal());
        driveRoute.setTollFare(route.getSummary().getTollFare());
        driveRoute.setFuelPrice(route.getSummary().getFuelPrice());
        driveRoute.setDuration(millToMinute(route.getSummary().getDuration()));
        driveRoute.setDistance(route.getSummary().getDistance());

        return driveRoute;
    }

    private String changeName(String parkingName) {
        StringBuffer sb = new StringBuffer(parkingName);
        return sb.indexOf("주차장") > -1 ? sb.toString() : sb.append(" 주차장").toString();
    }

    private String changeName(String stationName, String trafficType) {
        return trafficType.equals("BUS") ? stationName + " 정류장" : stationName + "역";
    }

    private void addStationString(RouteResponse.Path.Detail.DetailPath detailPath) {

    }

    private Integer millToMinute(Integer mill) {
        return (int) TimeUnit.MILLISECONDS.toMinutes(mill);
    }

}
