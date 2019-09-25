package com.bamplee.chomi.api.datatool.papago.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class PapagoResponse {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        private Result result;

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public static class Result {
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
    }
}
