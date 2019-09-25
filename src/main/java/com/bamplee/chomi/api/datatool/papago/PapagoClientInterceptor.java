package com.bamplee.chomi.api.datatool.papago;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PapagoClientInterceptor implements RequestInterceptor {
    @Value("${papago.id}")
    private String id;
    @Value("${papago.secret}")
    private String secret;

    @Override
    public void apply(RequestTemplate template) {
        template.header("X-NCP-APIGW-API-KEY-ID", id);
        template.header("X-NCP-APIGW-API-KEY", secret);
    }
}
