package com.hg.gxutapp.model;

import android.graphics.Bitmap;

public class AppData {
    private Bitmap img;
    private String name;
    private String url;

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
