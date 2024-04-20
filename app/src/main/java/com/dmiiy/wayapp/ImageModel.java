package com.dmiiy.wayapp;

public class ImageModel {
    private String url;

    public ImageModel() {} // Needed for Firebase

    public ImageModel(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

