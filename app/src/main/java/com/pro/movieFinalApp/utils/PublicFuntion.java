package com.pro.movieFinalApp.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.pro.movieFinalApp.MyApplication;
import com.pro.movieFinalApp.activity.PlayMovie;
import com.pro.movieFinalApp.model.Movie;
import com.pro.movieFinalApp.model.User;
import com.pro.movieFinalApp.model.UserInformation;
import com.pro.movieFinalApp.datamanager.DataStoreManager;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class PublicFuntion {

    public static void startActivity(Context context, Class<?> clz) {
        Intent intent = new Intent(context, clz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(context, clz);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void onClickItemMovie(Context context, Movie movie) {
        if (context == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("movie_id", movie.getId());
        startActivity(context, PlayMovie.class, bundle);
    }

    public static boolean isFavorite(Movie movie) {
        if (movie.getFavorite() == null || movie.getFavorite().isEmpty()) {
            return false;
        }
        List<UserInformation> listFavorite = new ArrayList<>(movie.getFavorite().values());
        if (listFavorite.isEmpty()) {
            return false;
        }
        for (UserInformation userInformation : listFavorite) {
            if (DataStoreManager.getUser().getEmail().equals(userInformation.getEmailUser())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHistory(Movie movie) {
        if (movie.getHistory() == null || movie.getHistory().isEmpty()) {
            return false;
        }
        List<UserInformation> listHistory = new ArrayList<>(movie.getHistory().values());
        if (listHistory.isEmpty()) {
            return false;
        }
        for (UserInformation userInformation : listHistory) {
            if (DataStoreManager.getUser().getEmail().equals(userInformation.getEmailUser())) {
                return true;
            }
        }
        return false;
    }

    public static UserInformation getUserInfor(Movie movie) {
        UserInformation userInformation = null;
        if (movie.getFavorite() == null || movie.getFavorite().isEmpty()) {
            return null;
        }
        List<UserInformation> listFavorite = new ArrayList<>(movie.getFavorite().values());
        if (listFavorite.isEmpty()) {
            return null;
        }
        for (UserInformation userInformationObject : listFavorite) {
            if (DataStoreManager.getUser().getEmail().equals(userInformationObject.getEmailUser())) {
                userInformation = userInformationObject;
                break;
            }
        }
        return userInformation;
    }

    public static void onClickFavoriteMovie(Context context, Movie movie, boolean isFavorite) {
        if (context == null) {
            return;
        }

        DatabaseReference databaseReference = MyApplication.get(context).getUserDatabaseReference();
        User user = new User(databaseReference);

        if (isFavorite) {
            String userEmail = DataStoreManager.getUser().getEmail();
            UserInformation userInformation = new UserInformation(System.currentTimeMillis(), userEmail);


            MyApplication.get(context).getMovieDatabaseReference()
                    .child(String.valueOf(movie.getId()))
                    .child("favorite")
                    .child(String.valueOf(userInformation.getId()))
                    .setValue(userInformation);

            MyApplication.get(context).getUserDatabaseReference()
                    .child(String.valueOf(user.getId()+1))
                    .child("favorite")
                    .child("ID "+String.valueOf(movie.getId()))
                    .setValue(movie.getTitle());
            return;
        }
        UserInformation userInformationFavorite = getUserInfor(movie);
        if (userInformationFavorite != null) {
            MyApplication.get(context).getMovieDatabaseReference()
                    .child(String.valueOf(movie.getId()))
                    .child("favorite")
                    .child(String.valueOf(userInformationFavorite.getId()))
                    .removeValue();

            MyApplication.get(context).getUserDatabaseReference()
                    .child(String.valueOf(user.getId()+1))
                    .child("favorite")
                    .child("ID "+String.valueOf(movie.getId()))
                    .removeValue();

        }
    }





}
