package com.bamplee.chomi.api.datatool.papago.dto;

public class PapagoRequest {
    private String source;
    private String target;
    private String text;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}