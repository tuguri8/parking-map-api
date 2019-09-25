package com.bamplee.chomi.api.application;

import com.bamplee.chomi.api.datatool.papago.PapagoClient;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoRequest;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Override
    public TranslateResponse translate(String source, String target, String text) {
        PapagoRequest papagoRequest = new PapagoRequest();
        papagoRequest.setSource(source);
        papagoRequest.setTarget(target);
        papagoRequest.setText(text);

        PapagoResponse papagoResponse = papagoClient.translate(id, secret, papagoRequest);
        return modelMapper.map(papagoResponse.getMessage().getResult(), TranslateResponse.class);
    }
}
