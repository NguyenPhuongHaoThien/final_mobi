package com.pro.movieFinalApp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pro.movieFinalApp.R;
import com.pro.movieFinalApp.model.User;
import com.pro.movieFinalApp.datamanager.DataStoreManager;
import com.pro.movieFinalApp.utils.PublicFuntion;
import com.pro.movieFinalApp.utils.StringUtil;

public class SignIn extends BaseActivity {

    private EditText editEmail;
    private EditText editPassword;
    private Button btnSignIn;
    private TextView tvForgotPassword;
    private LinearLayout layoutSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initUi();
        initListener();
    }

    private void initUi() {
        editEmail = findViewById(R.id.edt_email);
        editPassword = findViewById(R.id.edt_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        layoutSignUp = findViewById(R.id.layout_sign_up);
    }

    private void initListener() {
        layoutSignUp.setOnClickListener(
                v -> PublicFuntion.startActivity(SignIn.this, SignUp.class));

        btnSignIn.setOnClickListener(v -> onClickValidateSignIn());
        tvForgotPassword.setOnClickListener(v -> onClickForgotPassword());
    }

    private void onClickForgotPassword() {
        PublicFuntion.startActivity(this, ForgotPassword.class);
    }

    // kiểm tra tích hợp lệ của email password
    private void onClickValidateSignIn() {
        String strEmail = editEmail.getText().toString().trim();
        String strPassword = editPassword.getText().toString().trim();
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(SignIn.this, getString(R.string.email_require), Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isEmpty(strPassword)) {
            Toast.makeText(SignIn.this, getString(R.string.password_require), Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(SignIn.this, getString(R.string.email_invalid), Toast.LENGTH_SHORT).show();
        } else {
            signInUser(strEmail, strPassword);
        }
    }


    // xác thực và đăng nhập
    private void signInUser(String email, String password) {
        showProgressDialog(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    showProgressDialog(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            User userObject = new User(user.getEmail(), password);
                            DataStoreManager.setUser(userObject);
                            PublicFuntion.startActivity(SignIn.this, MainActivity.class);
                            finishAffinity();
                        }
                    } else {
                        Toast.makeText(SignIn.this, getString(R.string.sign_in_error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}