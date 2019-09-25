package com.bamplee.chomi.api.application;

import com.bamplee.chomi.api.datatool.papago.PapagoClient;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoRequest;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoResponse;

public class TranslateServicempl implements TranslateService{
    private final PapagoClient papagoClient;

    public TranslateServicempl(PapagoClient papagoClient) {this.papagoClient = papagoClient;}

    @Override
    public PapagoResponse translate(String source, String target, String text) {
        PapagoRequest papagoRequest = new PapagoRequest();
        papagoRequest.setSource(source);
        papagoRequest.setTarget(target);
        papagoRequest.setText(text);
        return papagoClient.translate(papagoRequest);
    }
}
