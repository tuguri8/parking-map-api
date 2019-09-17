package com.bamplee.chomi.api.datatool.sk;

import com.bamplee.chomi.api.datatool.sk.dto.WeatherHourlyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sk-client")
public interface SkClient {

    @GetMapping(value = "/weather/current/hourly")
    WeatherHourlyResponse getWeatherSummary(@RequestParam("appKey") String appKey,
                                            @RequestParam("version") String version,
                                            @RequestParam("lat") String lat,
                                            @RequestParam("lon") String lon);
}
