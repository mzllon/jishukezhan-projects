package com.jishukezhan.json.cfg;

public class Config {

    private boolean prettyFormat = false;

    private SerializationFeature[] serializationFeatures;

    public boolean isPrettyFormat() {
        return prettyFormat;
    }

    public void setPrettyFormat(boolean prettyFormat) {
        this.prettyFormat = prettyFormat;
    }

    public SerializationFeature[] getSerializationFeatures() {
        return serializationFeatures;
    }

    public void setSerializationFeatures(SerializationFeature[] serializationFeatures) {
        this.serializationFeatures = serializationFeatures;
    }

}
