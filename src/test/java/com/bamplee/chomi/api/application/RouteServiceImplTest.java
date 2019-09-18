package com.bamplee.chomi.api.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouteServiceImplTest {
    private static final Logger log = LoggerFactory.getLogger(RouteServiceImplTest.class);
    @Autowired
    RouteService routeService;

//    @Test
//    public void route() {
//        routeService.route("127.1058342", "37.359708", "126.9573151", "37.4962518");
//    }
}