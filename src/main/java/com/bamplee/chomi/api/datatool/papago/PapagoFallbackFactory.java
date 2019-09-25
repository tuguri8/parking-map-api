package com.bamplee.chomi.api.datatool.papago;

import com.bamplee.chomi.api.datatool.common.LoggingFallbackFactory;
import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsDirectionDrivingResponse;
import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsGcResponse;
import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsGeocodingResponse;
import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsSearchPlacesResponse;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoRequest;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PapagoFallbackFactory implements LoggingFallbackFactory<PapagoClient> {
    private static final PapagoClient FALLBACK = new PapagoClientFallback();

    @Override
    public PapagoClient fallback() {
        return FALLBACK;
    }

    @Override
    public Logger logger() {
        return null;
    }

    public static class PapagoClientFallback implements PapagoClient {

        @Override
        public PapagoResponse translate(PapagoRequest papagoRequest) {
            return null;
        }
    }
}
