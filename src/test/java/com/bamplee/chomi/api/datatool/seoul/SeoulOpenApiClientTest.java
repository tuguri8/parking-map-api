package com.bamplee.chomi.api.datatool.seoul;

import com.bamplee.chomi.api.datatool.seoul.dto.BikeListResponse;
import com.bamplee.chomi.api.datatool.seoul.dto.ForecastWarningResponse;
import com.bamplee.chomi.api.datatool.seoul.dto.GetParkInfoResponse;
import com.bamplee.chomi.api.datatool.seoul.dto.PublicBicycleRenTIdinfoResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeoulOpenApiClientTest {
    @Value("${seoul-openapi.key}")
    String key;

    @Autowired
    SeoulOpenApiClient seoulOpenApiClient;

    @Test
    public void getParkInfo() {
        int startIndex = 1;
        int endIndex = 1000;
        int pageSize = 1000;

        GetParkInfoResponse result = seoulOpenApiClient.getParkInfo(key, String.valueOf(startIndex), String.valueOf(endIndex));
        Arrays.stream(result.getParkInfo().getRow()).forEach(x -> {
            System.out.println(x.toString());
        });
        int totalSize = result.getParkInfo().getListTotalCount();
        int count = 0;
        while(true) {
            System.out.println(startIndex);
            System.out.println(endIndex);
            count += seoulOpenApiClient.getParkInfo(key, String.valueOf(startIndex), String.valueOf(endIndex)).getParkInfo().getRow().length;
            System.out.println();
            if(endIndex > totalSize) break;
            startIndex += pageSize;
            endIndex += pageSize;
        }
        System.out.println(count);
        assertEquals(result.getParkInfo().getResult().getCode(), "INFO-000");
    }

    @Test
    public void publicBicycleRenTIdinfo() {
        BikeListResponse result = seoulOpenApiClient.publicBicycleRenTIdinfo(key, "1", "1000");
        assertEquals(result.getRentBikeStatus().getResult().getCode(), "INFO-000");
    }

    @Test
    public void forecastWarningMinuteParticleOfDustService() {
        ForecastWarningResponse stringObjectMap = seoulOpenApiClient.forecastWarningMinuteParticleOfDustService(key, "1", "1000");
        System.out.println(stringObjectMap.toString());
    }

//    @Test
//    public void realTimeWeatherObserveInfo() {
//        String yyyyMMddhh = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhh"));
//        Map<String,Object> result = seoulOpenApiClient.realTimeWeatherObserveInfo(key, "1", "1000", yyyyMMddhh);
//        System.out.println(result.toString());
//    }
}