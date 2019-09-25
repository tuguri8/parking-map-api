package com.bamplee.chomi.api.application;

import com.bamplee.chomi.api.datatool.papago.dto.PapagoResponse;

public interface TranslateService {
    PapagoResponse translate(String source, String target, String text);
}
