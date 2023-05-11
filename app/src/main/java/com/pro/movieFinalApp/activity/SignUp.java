package com.pro.movieFinalApp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.pro.movieFinalApp.R;
import com.pro.movieFinalApp.model.User;
import com.pro.movieFinalApp.datamanager.DataStoreManager;
import com.pro.movieFinalApp.utils.PublicFuntion;
import com.pro.movieFinalApp.utils.StringUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;


public class SignUp extends BaseActivity {

    private EditText edtEmail;
    private EditText edtPassword;
    private EditText re_edtPassword;
    private Button btnSignUp;
    private LinearLayout layoutSignIn;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUi();
        initListener();
    }

    private void initUi() {
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        re_edtPassword = findViewById(R.id.re_edt_password);
        btnSignUp = findViewById(R.id.btn_sign_up);
        layoutSignIn = findViewById(R.id.layout_sign_in);
        imgBack = findViewById(R.id.img_back);
    }

    private void initListener() {
        imgBack.setOnClickListener(v -> onBackPressed());
        layoutSignIn.setOnClickListener(v -> finish());
        btnSignUp.setOnClickListener(v -> onClickValidateSignUp());
    }

    private void isEmailExists(String email, OnCompleteListener<AuthResult> onCompleteListener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, "password")
                .addOnCompleteListener(onCompleteListener);
    }



    private void onClickValidateSignUp() {
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        String re_strPassword = re_edtPassword.getText().toString().trim();
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(SignUp.this, getString(R.string.email_require), Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isEmpty(strPassword)) {
            Toast.makeText(SignUp.this, getString(R.string.password_require), Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(SignUp.this, getString(R.string.email_invalid), Toast.LENGTH_SHORT).show();
        }else if(StringUtil.isEmpty(re_strPassword)){
            Toast.makeText(SignUp.this, getString(R.string.re_password_require), Toast.LENGTH_SHORT).show();
        }else if(!(strPassword.equals(re_strPassword))){
            Toast.makeText(SignUp.this, getString(R.string.equal_password), Toast.LENGTH_SHORT).show();
        }
        else {
            isEmailExists(strEmail, task -> {
                if (task.isSuccessful()) {
                    // Email is in use
                    Toast.makeText(SignUp.this, getString(R.string.email_exist), Toast.LENGTH_SHORT).show();
                } else {
                    // Email is valid and not in use
                    if (StringUtil.isEmpty(strPassword)) {
                        Toast.makeText(SignUp.this, getString(R.string.password_require), Toast.LENGTH_SHORT).show();
                    } else if (StringUtil.isEmpty(re_strPassword)){
                        Toast.makeText(SignUp.this, getString(R.string.re_password_require), Toast.LENGTH_SHORT).show();
                    } else if(!(strPassword.equals(re_strPassword))){
                        Toast.makeText(SignUp.this, getString(R.string.equal_password), Toast.LENGTH_SHORT).show();
                    } else {
                        signUpUser(strEmail, strPassword);
                    }
                }
            });
        }
    }




    private void signUpUser(String email, String password) {
        showProgressDialog(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    showProgressDialog(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            DatabaseReference countRef = FirebaseDatabase.getInstance().getReference("userCount");
                            countRef.runTransaction(new Transaction.Handler() {
                                @Override
                                public Transaction.Result doTransaction(MutableData mutableData) {
                                    Long count = mutableData.getValue(Long.class);
                                    if (count == null) {
                                        count = 0L;
                                    }
                                    mutableData.setValue(count + 1);
                                    return Transaction.success(mutableData);
                                }
                                @Override
                                public void onComplete(DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                                    if (committed) {
                                        Long count = dataSnapshot.getValue(Long.class);
                                        int countValue = count.intValue();
                                        if (count != null) {
                                            User userObject = new User(email, password);
                                            userObject.setId(countValue);
                                            DataStoreManager.setUser(userObject);
                                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                                            usersRef.child(String.valueOf(count)).setValue(userObject);

                                            // Chuyển đến màn hình MainActivity
                                            PublicFuntion.startActivity(SignUp.this, MainActivity.class);
                                            finishAffinity();
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(SignUp.this, getString(R.string.sign_up_error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}