package com.bamplee.chomi.api.interfaces.place;

import com.bamplee.chomi.api.application.MapService;
import com.bamplee.chomi.api.application.ParkingSyncService;
import com.bamplee.chomi.api.application.RouteService;
import com.bamplee.chomi.api.application.TranslateResponse;
import com.bamplee.chomi.api.application.TranslateService;
import com.bamplee.chomi.api.application.WeatherResponse;
import com.bamplee.chomi.api.application.WeatherService;
import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsSearchPlacesResponse;
import com.bamplee.chomi.api.datatool.odsay.dto.OdSayLoadLaneResponse;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoResponse;
import com.bamplee.chomi.api.infrastructure.persistence.jpa.entity.ParkingInfo;
import com.bamplee.chomi.api.interfaces.place.dto.response.V2RouteResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("api/v1/maps")
public class MapController {
    private final MapService mapService;
    private final RouteService routeService;
    private final WeatherService weatherService;
    private final ParkingSyncService parkingSyncService;
    private final TranslateService translateService;

    public MapController(MapService mapService,
                         RouteService routeService,
                         WeatherService weatherService,
                         ParkingSyncService parkingSyncService, TranslateService translateService) {
        this.mapService = mapService;
        this.routeService = routeService;
        this.weatherService = weatherService;
        this.parkingSyncService = parkingSyncService;
        this.translateService = translateService;
    }

    @GetMapping("search")
    public NaverMapsSearchPlacesResponse search(@RequestParam("query") String query) {
        return mapService.search(query);
    }

    @GetMapping("route")
    public V2RouteResponse route(@RequestParam("startX") String startX,
                                 @RequestParam("startY") String startY,
                                 @RequestParam("endX") String endX,
                                 @RequestParam("endY") String endY,
                                 @RequestParam(value = "sort", defaultValue = "best") String sort,
                                 @RequestParam(value = "parkType", defaultValue = "ALL") String parkType) {
        return routeService.route(startX, startY, endX, endY, sort, parkType);
    }

    @GetMapping(value = "image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@RequestParam("x") String x, @RequestParam("y") String y) {
        return mapService.image(x, y);
    }

    @GetMapping("graph")
    public OdSayLoadLaneResponse graph(@RequestParam("mapObject") String mapObject) {
        return mapService.graph(mapObject);
    }

    @GetMapping("weather")
    public WeatherResponse weather(@RequestParam("lat") String lat,
                                   @RequestParam("lon") String lon) {
        return weatherService.getWeather(lat, lon);
    }

    @GetMapping("parking")
    public ParkingInfo parking(@RequestParam("code") String parkingCode) {
        return parkingSyncService.getParkingInfo(parkingCode);
    }

    @GetMapping("sync")
    public void parkingSync() {
        parkingSyncService.syncParkingInfoList();
    }

    @GetMapping("translate")
    public TranslateResponse translate(@RequestParam("source") String source,
                                       @RequestParam("target") String target,
                                       @RequestParam("text") String text) {
        return translateService.translate(source, target, text);
    }
}
