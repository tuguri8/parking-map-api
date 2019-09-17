package com.bamplee.chomi.api.application;

import com.bamplee.chomi.api.datatool.airkorea.AirkoreaClient;
import com.bamplee.chomi.api.datatool.airkorea.dto.AirkoreaResponse;
import com.bamplee.chomi.api.datatool.sk.SkClient;
import com.bamplee.chomi.api.datatool.sk.dto.WeatherHourlyResponse;
import com.bamplee.chomi.api.infrastructure.persistence.jpa.entity.Air;
import com.bamplee.chomi.api.infrastructure.persistence.jpa.repository.AirRepository;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class WeatherServicempl implements WeatherService {
    @Value("${airkorea-client.key}")
    String key;

    @Value("${sk-client.key}")
    String skKey;

    private static final Logger log = LoggerFactory.getLogger(WeatherServicempl.class);

    private final AirkoreaClient airkoreaClient;
    private final AirRepository airRepository;
    private final SkClient skClient;

    public WeatherServicempl(AirkoreaClient airkoreaClient,
                             AirRepository airRepository,
                             SkClient skClient) {
        this.airkoreaClient = airkoreaClient;
        this.airRepository = airRepository;
        this.skClient = skClient;
    }

    @Override
    @Scheduled(cron = "0 29 * * * *")
    public void syncAir() {
        AirkoreaResponse airkoreaResponse = airkoreaClient.getAir(key, "1", "1", "PM10", "HOUR", "WEEK");
        List<Air> airList = Lists.newArrayList();
        AirkoreaResponse.Item item = airkoreaResponse.getBody().getItems().getItem();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        airList.add(new Air.Builder("경기",
                                    NumberUtils.toInt(item.getBusan()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getBusan())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("충북",
                                    NumberUtils.toInt(item.getChungbuk()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getChungbuk())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("충남",
                                    NumberUtils.toInt(item.getChungnam()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getChungnam())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("대구",
                                    NumberUtils.toInt(item.getDaegu()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getDaegu())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("대전",
                                    NumberUtils.toInt(item.getDaejeon()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getDaejeon())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("강원",
                                    NumberUtils.toInt(item.getGangwon()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getGangwon())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("광주",
                                    NumberUtils.toInt(item.getGwangju()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getGwangju())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("경북",
                                    NumberUtils.toInt(item.getGyeongbuk()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getGyeongbuk())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("경기",
                                    NumberUtils.toInt(item.getGyeonggi()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getGyeonggi())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("경남",
                                    NumberUtils.toInt(item.getGyeongnam()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getGyeongnam())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("인천",
                                    NumberUtils.toInt(item.getIncheon()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getIncheon())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("제주",
                                    NumberUtils.toInt(item.getJeju()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getJeju())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("전북",
                                    NumberUtils.toInt(item.getJeonbuk()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getJeonbuk())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("전남",
                                    NumberUtils.toInt(item.getJeonnam()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getJeonnam())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("세종",
                                    NumberUtils.toInt(item.getSejong()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getSejong())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("서울",
                                    NumberUtils.toInt(item.getSeoul()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getSeoul())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airList.add(new Air.Builder("울산",
                                    NumberUtils.toInt(item.getUlsan()),
                                    getAirGradeFromValue(NumberUtils.toInt(item.getUlsan())),
                                    LocalDateTime.parse(item.getDataTime(), dateTimeFormatter)).build());
        airRepository.saveAll(airList);
        log.info("미세먼지 저장 완료 : " + item.getDataTime() + " " + airList.size() + "개");
    }

    @Override
    public WeatherResponse getWeather(String lat, String lon) {
        WeatherHourlyResponse weatherHourlyResponse = skClient.getWeatherSummary(skKey, "2", lat, lon);
        Air air = airRepository.findTopByAreaNameOrderByIdDesc(weatherHourlyResponse.getWeather().getHourly().get(0).getGrid().getCity())
                               .orElseThrow(() -> new RuntimeException("미세먼지 정보가 존재하지 않습니다"));

        return transform(weatherHourlyResponse, air);
    }

    private String getAirGradeFromValue(Integer airValue) {
        String grade = "낮음";
        if (airValue > 150) {
            grade = "매우나쁨";
        } else if (airValue > 80) {
            grade = "나쁨";
        } else if (airValue > 30) {
            grade = "보통";
        }
        return grade;
    }

    private WeatherResponse transform(WeatherHourlyResponse weatherHourlyResponse, Air air) {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setAirGrade(air.getAirGrade());
        weatherResponse.setAirValue(air.getAirValue());
        weatherResponse.setItemCode(air.getItemCode());

        WeatherHourlyResponse.Weather.Hourly hourly = weatherHourlyResponse.getWeather().getHourly().get(0);
        weatherResponse.setCity(hourly.getGrid().getCity());
        weatherResponse.setCounty(hourly.getGrid().getCounty());
        weatherResponse.setVillage(hourly.getGrid().getVillage());
        weatherResponse.setHumidity(hourly.getHumidity());
        weatherResponse.setSkyName(hourly.getSky().getName());
        weatherResponse.setTc(hourly.getTemperature().getTc());
        weatherResponse.setTmax(hourly.getTemperature().getTmax());
        weatherResponse.setTmin(hourly.getTemperature().getTmin());
        return weatherResponse;
    }
}
