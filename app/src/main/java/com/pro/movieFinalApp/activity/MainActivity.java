package com.pro.movieFinalApp.activity;

import android.os.Bundle;
import android.widget.TextView;

import android.widget.ImageView;
import androidx.viewpager2.widget.ViewPager2;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pro.movieFinalApp.R;
import com.pro.movieFinalApp.adapter.ViewPager;

public class MainActivity extends BaseActivity {

    private BottomNavigationView mBottomNavigationView;
    private ViewPager2 mViewPager2;
    private TextView tvTitle;
    private ImageView mImgComment;


//    mImgComment = findViewById(R.id.img_comment);
//        mImgComment.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Fragment fragment = new CommentFragment();
//            getSupportFragmentManager().beginTransaction().replace(id.bottom_navigation, fragment).commit();
//        }
//    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tv_title);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mViewPager2 = findViewById(R.id.viewpager_2);
        mViewPager2.setUserInputEnabled(false);
        ViewPager myViewPagerAdapter = new ViewPager(this);
        mViewPager2.setAdapter(myViewPagerAdapter);




        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        tvTitle.setText(getString(R.string.nav_home));
                        break;

                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_favorite).setChecked(true);
                        tvTitle.setText(getString(R.string.nav_favorite));
                        break;

                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_history).setChecked(true);
                        tvTitle.setText(getString(R.string.nav_history));
                        break;

                    case 3:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
                        tvTitle.setText(getString(R.string.nav_account));
                        break;
                }
            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                mViewPager2.setCurrentItem(0);
                tvTitle.setText(getString(R.string.nav_home));
            } else if (id == R.id.nav_favorite) {
                mViewPager2.setCurrentItem(1);
                tvTitle.setText(getString(R.string.nav_favorite));
            } else if (id == R.id.nav_history) {
                mViewPager2.setCurrentItem(2);
                tvTitle.setText(getString(R.string.nav_history));
            } else if (id == R.id.nav_account) {
                mViewPager2.setCurrentItem(3);
                tvTitle.setText(getString(R.string.nav_account));
            }
            return true;
        });



    }

    @Override
    public void onBackPressed() {
        showConfirmExitApp();
    }

    private void showConfirmExitApp() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.app_name))
                .content(getString(R.string.exit_app))
                .positiveText(getString(R.string.ok))
                .onPositive((dialog, which) -> finish())
                .negativeText(getString(R.string.cancel))
                .cancelable(false)
                .show();
    }
}
