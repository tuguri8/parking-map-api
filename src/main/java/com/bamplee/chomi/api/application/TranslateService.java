package com.bamplee.chomi.api.application;

public interface TranslateService {
    TranslateResponse translate(String source, String target, String text);
}
