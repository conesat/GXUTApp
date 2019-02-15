package com.hg.gxutapp.model;

public class News {
    private String url;
    private String imgUrl;
    private String title;
    private String content;
    private String previewContent;

    @Override
    public String toString() {
        return "News{" +
                "url='" + url + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", previewContent='" + previewContent + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {

        return url;
    }

    private String time;

    public void setImgUrl(String imgUrl) {

        this.imgUrl = imgUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPreviewContent(String previewContent) {
        this.previewContent = previewContent;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgUrl() {

        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPreviewContent() {
        return previewContent;
    }

    public String getTime() {
        return time;
    }
}
