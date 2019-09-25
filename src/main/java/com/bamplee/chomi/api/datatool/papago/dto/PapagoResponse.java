package com.bamplee.chomi.api.datatool.papago.dto;

public class PapagoResponse {
    private String srcLangType;
    private String tarLangType;
    private String translatedText;

    public String getSrcLangType() {
        return srcLangType;
    }

    public void setSrcLangType(String srcLangType) {
        this.srcLangType = srcLangType;
    }

    public String getTarLangType() {
        return tarLangType;
    }

    public void setTarLangType(String tarLangType) {
        this.tarLangType = tarLangType;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}
