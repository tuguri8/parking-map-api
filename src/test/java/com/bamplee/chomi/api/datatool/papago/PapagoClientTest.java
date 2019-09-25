package com.bamplee.chomi.api.datatool.papago;

import com.bamplee.chomi.api.datatool.papago.dto.PapagoRequest;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoResponse;
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
public class PapagoClientTest {

    private static final Logger log = LoggerFactory.getLogger(PapagoClientTest.class);

    @Value("${papago.id}")
    private String id;
    @Value("${papago.secret}")
    private String secret;
    @Autowired
    private PapagoClient papagoClient;

    @Test
    public void translate() {
        PapagoRequest papagoRequest = new PapagoRequest();
        papagoRequest.setSource("en");
        papagoRequest.setTarget("ko");
        papagoRequest.setText("hello");
        papagoClient.translate(id, secret, papagoRequest);
    }

}