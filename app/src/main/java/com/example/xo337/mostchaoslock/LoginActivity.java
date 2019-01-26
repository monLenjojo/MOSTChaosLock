package com.example.xo337.mostchaoslock;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.xo337.mostchaoslock.netWorkCheck.NetWorkCheck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText edEmail,edPassword;
    NetWorkCheck netWorkCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edEmail = findViewById(R.id.edLoginEmail);
        edPassword = findViewById(R.id.edLoginPassword);
        auth = FirebaseAuth.getInstance();
        netWorkCheck = new NetWorkCheck(this);
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent page = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(page);
                    LoginActivity.this.finish();
                }
            }
        });
    }

    public void loginButton(View view) {
        view.setEnabled(false);
        netWorkCheck.netWorkCheck();
        Boolean checkState = true;
        edEmail.setError(null);
        edPassword.setError(null);
        final String email = edEmail.getText().toString();
        final String password = edPassword.getText().toString();
        if (!Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$").matcher(email).matches()) {
            edEmail.setError("請輸入正確格式的帳號");
            checkState = false;
        }
        if (!Pattern.compile("[[a-zA-Z-]+[0-9-]]{6,12}$").matcher(password).matches()) {
            edPassword.setError("請輸入6~12位中英文混合密碼");
            checkState = false;
        }
        if (checkState) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isComplete()) {
                        if (task.isSuccessful()) {
                            Intent page = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(page);
                        }else{
                            new AlertDialog.Builder(LoginActivity.this).setMessage("Have not find this account")
                                    .setPositiveButton("get new one", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent page = new Intent(LoginActivity.this,RegisterActivity.class);
                                            page.putExtra("EMAIL",email);
                                            page.putExtra("PASSWORD",password);
                                            startActivity(page);
                                        }
                                    })
                                    .setNegativeButton("try again",null)
                                    .show();
                        }
                    }
                }
            });
        }
        view.setEnabled(true);
    }
}
