package com.pro.movieFinalApp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.pro.movieFinalApp.R;
import com.pro.movieFinalApp.datamanager.DataStoreManager;
import com.pro.movieFinalApp.utils.PublicFuntion;
import com.pro.movieFinalApp.utils.StringUtil;

@SuppressLint("CustomSplashScreen")
public class Splash extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(this::goToActivity, 2000);
    }

    private void goToActivity() {
        if (DataStoreManager.getUser() != null && !StringUtil.isEmpty(DataStoreManager.getUser().getEmail())) {
            PublicFuntion.startActivity(this, MainActivity.class);
        } else {
            PublicFuntion.startActivity(this, SignIn.class);
        }
        finish();
    }
}
