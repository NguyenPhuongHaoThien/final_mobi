package com.pro.movieFinalApp.model;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

public class User {


    private  int id;
    private String email;
    private String password;
    private DatabaseReference databaseReference;

    public User() {
    }
    public User(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }



    public User(String email, String password,int id) {
        this.id =id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toJSon() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
