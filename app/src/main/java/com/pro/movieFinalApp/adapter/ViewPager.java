package com.pro.movieFinalApp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pro.movieFinalApp.fragment.AccountFragment;
import com.pro.movieFinalApp.fragment.FavoriteFragment;
import com.pro.movieFinalApp.fragment.HistoryFragment;
import com.pro.movieFinalApp.fragment.HomeFragment;

public class ViewPager extends FragmentStateAdapter {

    public ViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new FavoriteFragment();

            case 2:
                return new HistoryFragment();

            case 3:
                return new AccountFragment();

            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
