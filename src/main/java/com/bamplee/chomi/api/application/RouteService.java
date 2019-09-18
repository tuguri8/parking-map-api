package com.bamplee.chomi.api.application;

import com.bamplee.chomi.api.interfaces.place.dto.response.V2RouteResponse;

public interface RouteService {
    V2RouteResponse route(String departureX, String departureY, String destinationX, String destinationY, String sortType, String parkType);
}
