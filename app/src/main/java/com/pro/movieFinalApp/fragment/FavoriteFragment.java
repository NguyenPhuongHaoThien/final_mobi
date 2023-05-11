package com.pro.movieFinalApp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pro.movieFinalApp.MyApplication;
import com.pro.movieFinalApp.R;
import com.pro.movieFinalApp.adapter.MovieAdapter;
import com.pro.movieFinalApp.model.Movie;
import com.pro.movieFinalApp.utils.PublicFuntion;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private View mView;
    private RecyclerView rcvFavorite;

    private List<Movie> listMovies;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_favorite, container, false);

        initUi();
        getListFavorites();

        return mView;
    }

    private void initUi() {
        rcvFavorite = mView.findViewById(R.id.rcv_favorite);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rcvFavorite.setLayoutManager(gridLayoutManager);
    }

    private void getListFavorites() {
        if (getActivity() == null) {
            return;
        }
        MyApplication.get(getActivity()).getMovieDatabaseReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (listMovies != null) {
                            listMovies.clear();
                        } else {
                            listMovies = new ArrayList<>();
                        }
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Movie movie = dataSnapshot.getValue(Movie.class);
                            if (movie != null && PublicFuntion.isFavorite(movie)) {
                                listMovies.add(0, movie);
                            }
                        }
                        displayListHistories();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), getString(R.string.date_error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayListHistories() {
        MovieAdapter movieAdapter = new MovieAdapter(listMovies, new MovieAdapter.IClickItemListener() {
            @Override
            public void onClickItem(Movie movie) {
                PublicFuntion.onClickItemMovie(getActivity(), movie);
            }

            @Override
            public void onClickFavorite(Movie movie, boolean favorite) {
                PublicFuntion.onClickFavoriteMovie(getActivity(), movie, favorite);
            }
        });
        rcvFavorite.setAdapter(movieAdapter);
    }
}
