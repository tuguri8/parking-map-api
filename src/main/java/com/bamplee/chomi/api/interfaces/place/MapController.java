package com.bamplee.chomi.api.interfaces.place;

import com.bamplee.chomi.api.application.MapService;
import com.bamplee.chomi.api.application.RouteResponse;
import com.bamplee.chomi.api.application.RouteService;
import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsSearchPlacesResponse;
import com.bamplee.chomi.api.datatool.odsay.dto.OdSayLoadLaneResponse;
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

    public MapController(MapService mapService, RouteService routeService) {
        this.mapService = mapService;
        this.routeService = routeService;
    }

    @GetMapping("search")
    public NaverMapsSearchPlacesResponse search(@RequestParam("query") String query) {
        return mapService.search(query);
    }

    @GetMapping("route")
    public RouteResponse route(@RequestParam("startX") String startX,
                               @RequestParam("startY") String startY,
                               @RequestParam("endX") String endX,
                               @RequestParam("endY") String endY) {
        return routeService.route(startX, startY, endX, endY);
    }

    @GetMapping(value = "image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@RequestParam("x") String x, @RequestParam("y") String y) {
        return mapService.image(x, y);
    }

    @GetMapping("graph")
    public OdSayLoadLaneResponse graph(@RequestParam("mapObject") String mapObject) {
        return mapService.graph(mapObject);
    }
}
