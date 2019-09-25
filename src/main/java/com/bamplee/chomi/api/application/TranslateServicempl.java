package com.bamplee.chomi.api.application;

import com.bamplee.chomi.api.datatool.papago.PapagoClient;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoRequest;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class TranslateServicempl implements TranslateService {
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
        papagoRequest.setText(URLDecoder.decode(text, StandardCharsets.UTF_8));

        PapagoResponse papagoResponse = papagoClient.translate(id, secret, papagoRequest);
        return modelMapper.map(papagoResponse.getMessage().getResult(), TranslateResponse.class);
    }
}
