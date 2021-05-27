package com.vogella.StayActiveApp;

public class WorkoutData {
    private String title, description, url, imageUrl, videoUrl, score, stepdata;

    public WorkoutData() {
    }

    public WorkoutData(String title, String description, String url, String imageUrl, String videoUrl, String score, String stepdata) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.score = score;
        this.stepdata = stepdata;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStepdata() {
        return stepdata;
    }

    public void setStepdata(String stepdata) {
        this.stepdata = stepdata;
    }
}
