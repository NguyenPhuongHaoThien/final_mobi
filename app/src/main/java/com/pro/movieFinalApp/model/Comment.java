package com.pro.movieFinalApp.model;

public class Comment {
    private String userId;
    private String movieId;
    private String comment;

    public Comment() {}

    public Comment(String userId, String movieId, String comment) {
        this.userId = userId;
        this.movieId = movieId;
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
