package com.pro.movieFinalApp.activity;

import com.google.firebase.auth.FirebaseAuth;
import com.pro.movieFinalApp.R;
import com.pro.movieFinalApp.utils.StringUtil;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class ForgotPassword extends BaseActivity {

    private EditText edtEmail;
    private Button btnResetPassword;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initUi();
        initListener();
    }

    private void initUi() {
        edtEmail = findViewById(R.id.edt_email);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        imgBack = findViewById(R.id.img_back);
    }

    private void initListener() {
        imgBack.setOnClickListener(v -> onBackPressed());
        btnResetPassword.setOnClickListener(v -> onClickValidateResetPassword());
    }

    private void onClickValidateResetPassword() {
        String strEmail = edtEmail.getText().toString().trim();
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(ForgotPassword.this, getString(R.string.email_require), Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(ForgotPassword.this, getString(R.string.email_invalid), Toast.LENGTH_SHORT).show();
        } else {
            resetPassword(strEmail);
        }
    }

    private void resetPassword(String email) {
        showProgressDialog(true);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    showProgressDialog(false);
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPassword.this,
                                getString(R.string.reset_password_successfully),
                                Toast.LENGTH_SHORT).show();
                        edtEmail.setText("");
                    }
                });
    }
}