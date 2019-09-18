package com.bamplee.chomi.api.datatool.sk;

import com.bamplee.chomi.api.datatool.sk.dto.WeatherHourlyResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkClientTest {
    private static final Logger log = LoggerFactory.getLogger(SkClientTest.class);
    @Value("${sk-client.key}")
    String apiKey;

    @Autowired
    SkClient skClient;

    @Test
    public void weatherTest() {
        WeatherHourlyResponse weatherHourlyResponse = skClient.getWeatherSummary(apiKey, "2", "37.4007650", "127.1121956");
        log.info("결과" + weatherHourlyResponse.getWeather().toString());
    }
}