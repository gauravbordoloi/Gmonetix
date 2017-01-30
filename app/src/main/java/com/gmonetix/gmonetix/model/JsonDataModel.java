package com.gmonetix.gmonetix.model;

public class JsonDataModel {
    private int id;
    private String date;
    private String link;
    private String title_rendered;
    private String featured_media_url;

    public String getFeaturedMediaUrl() {
        return featured_media_url;
    }

    public void setFeaturedMediaUrl(String featured_media_url) {
        this.featured_media_url = featured_media_url;
    }

    public String getTitleRendered() {
        return title_rendered;
    }

    public void setTitleRendered(String title_rendered) {
        this.title_rendered = title_rendered;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
