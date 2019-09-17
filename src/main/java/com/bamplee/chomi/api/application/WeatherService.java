package com.bamplee.chomi.api.application;

public interface WeatherService {
    void syncAir();

    WeatherResponse getWeather(String lat, String lon);
}
