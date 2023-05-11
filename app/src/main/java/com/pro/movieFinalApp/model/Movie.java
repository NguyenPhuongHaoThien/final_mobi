package com.pro.movieFinalApp.model;

import java.io.Serializable;
import java.util.HashMap;

public class Movie implements Serializable {

    private int id;
    private String title;
    private String image;
    private String url;
    private boolean featured;
    private String banner;

    private HashMap<String, UserInformation> favorite;
    private HashMap<String, UserInformation> history;

    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, UserInformation> getFavorite() {
        return favorite;
    }

    public void setFavorite(HashMap<String, UserInformation> favorite) {
        this.favorite = favorite;
    }

    public HashMap<String, UserInformation> getHistory() {
        return history;
    }

    public void setHistory(HashMap<String, UserInformation> history) {
        this.history = history;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
