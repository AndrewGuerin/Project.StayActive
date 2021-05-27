package com.vogella.StayActiveApp;

import java.io.Serializable;

public class MyWorkoutsRetrieve implements Serializable {
    //Model class

    private String title, imageUrl, url, description, videoUrl;

    //construtor
    public MyWorkoutsRetrieve() {

    }

    public MyWorkoutsRetrieve(String title, String imageUrl, String url, String description, String videoUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.url = url;
        this.description = description;
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
