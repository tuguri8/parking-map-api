package com.bamplee.chomi.api.datatool.papago;

import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsDirectionDrivingResponse;
import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsGcResponse;
import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsGeocodingResponse;
import com.bamplee.chomi.api.datatool.naver.dto.NaverMapsSearchPlacesResponse;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoRequest;
import com.bamplee.chomi.api.datatool.papago.dto.PapagoResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "papago", url = "https://naveropenapi.apigw.ntruss.com", fallback = PapagoFallbackFactory.class)
public interface PapagoClient {
    @PostMapping("nmt/v1/translation")
    PapagoResponse translate(@RequestBody PapagoRequest papagoRequest);
}
