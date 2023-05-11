package com.pro.movieFinalApp.model;

import java.io.Serializable;

public class UserInformation implements Serializable {

    private long id;
    private String emailUser;

    public UserInformation() {}

    public UserInformation(long id, String emailUser) {
        this.id = id;
        this.emailUser = emailUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }
}
