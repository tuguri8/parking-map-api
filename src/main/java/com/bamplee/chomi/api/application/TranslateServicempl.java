package com.bamplee.chomi.api.application;

import com.bamplee.chomi.api.datatool.papago.PapagoClient;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoRequest;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Service
public class TranslateServicempl implements TranslateService {
    private static final Logger log = LoggerFactory.getLogger(TranslateServicempl.class);

    @Value("${papago.id}")
    private String id;
    @Value("${papago.secret}")
    private String secret;
    private final PapagoClient papagoClient;
    private final ModelMapper modelMapper;

    public TranslateServicempl(PapagoClient papagoClient, ModelMapper modelMapper) {
        this.papagoClient = papagoClient;
        this.modelMapper = modelMapper;
    }

    @Cacheable(value = "translate")
    @Override
    public TranslateResponse translate(String source, String target, String text) {
        PapagoRequest papagoRequest = new PapagoRequest();
        papagoRequest.setSource(source);
        papagoRequest.setTarget(target);
        try {
            papagoRequest.setText(URLDecoder.decode(text, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }

        PapagoResponse papagoResponse = papagoClient.translate(id, secret, papagoRequest);
        return modelMapper.map(papagoResponse.getMessage().getResult(), TranslateResponse.class);
    }
}
