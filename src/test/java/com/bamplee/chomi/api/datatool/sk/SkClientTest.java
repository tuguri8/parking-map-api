package com.bamplee.chomi.api.datatool.sk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkClientTest {
    @Value("${sk-client.key}")
    String apiKey;

    @Autowired
    SkClient skClient;

    @Test
    public void weatherTest() {
        skClient.getWeatherSummary(apiKey, "2", "127.1121956", "37.4007650");
    }
}