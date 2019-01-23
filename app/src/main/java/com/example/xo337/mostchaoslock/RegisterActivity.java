package com.example.xo337.mostchaoslock;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.xo337.mostchaoslock.userInformation.JavaBeanSetPerson;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    Button buttonDone;
    EditText edEmail,edPassword,edCKPassword,edPhone,edName;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        buttonDone = findViewById(R.id.buttonRegister);
        buttonDone.setEnabled(false);
        CheckBox checkAgree = findViewById(R.id.checkBoxRegister);
        checkAgree.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    buttonDone.setEnabled(b);
            }
        });
        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");
        String password = intent.getStringExtra("PASSWORD");
        edEmail = findViewById(R.id.edRegisterEmail);
        edPassword = findViewById(R.id.edRegisterPassword);
        edCKPassword = findViewById(R.id.edRegisterCKPassword);
        edPhone = findViewById(R.id.edRegisterPhone);
        edName = findViewById(R.id.edRegisterUserName);
        edEmail.setText(email);
        edPassword.setText(password);
        auth = FirebaseAuth.getInstance();
    }

    public void registerButton(View view){
        View focusView = null;
        Boolean checkData=true;
        final String phone = edPhone.getText().toString();
        String ckPassword = edCKPassword.getText().toString();
        String password = edPassword.getText().toString();
        final String email = edEmail.getText().toString();
        final String name = edName.getText().toString();
        edName.setError(null);
        edEmail.setError(null);
        edPassword.setError(null);
        edCKPassword.setError(null);
        edPhone.setError(null);
        if (!TextUtils.isEmpty(phone)) {
            if (Pattern.compile("[0]{1}[9]{1}[0-9]{2}[0-9]{6}").matcher(phone).matches()) {
                //正確
            } else {
                edPhone.setError("請輸入正確的手機號碼");
                focusView = edPhone;
                checkData = false;
            }
        } else {
            edPhone.setError("請輸入手機號碼");
            focusView = edPhone;
            checkData = false;
        }
        if(!TextUtils.isEmpty(ckPassword)){
            if(Pattern.compile("[[a-zA-Z-]+[0-9-]]{6,12}$").matcher(ckPassword).matches()){
                if (password.equals(ckPassword)) {
                    //正確
                }else {
                    edCKPassword.setError("與密碼不相同");
                    focusView = edCKPassword;
                    checkData = false;
                }
            }else {
                edCKPassword.setError("請輸入中英文混和6~12密碼");
                focusView = edCKPassword;
                checkData = false;
            }
        }else {
            edCKPassword.setError("請輸入中英文混和6~12密碼");
            focusView = edCKPassword;
            checkData = false;
        }
        if(!TextUtils.isEmpty(password)){
            if(!Pattern.compile("[[a-zA-Z-]+[0-9-]]{6,12}$").matcher(password).matches()){
                edPassword.setError("請輸入中英文混和6~12密碼");
                focusView = edPassword;
                checkData = false;
            }
        }else {
            edPassword.setError("請輸入中英文混和6~12密碼");
            focusView = edPassword;
            checkData = false;
        }
        if(!TextUtils.isEmpty(email)){
            if(!Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$").matcher(email).matches()){
                edEmail.setError("Email格式錯誤");
                focusView = edEmail;
                checkData = false;
            }
        }else {
            edEmail.setError("不可為空");
            focusView = edEmail;
            checkData = false;
        }
        if (TextUtils.isEmpty(name)) {
            edName.setError("不可為空");
            focusView = edName;
            checkData = false;
        }

        if (checkData){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isComplete()) {
                        if (task.isSuccessful()) {
                            JavaBeanSetPerson data = new JavaBeanSetPerson(name, email, phone);
                            FirebaseDatabase.getInstance().getReference("userList").child(auth.getUid()).setValue(data);
                        }
                    }
                }
            });
        }else{
            focusView.requestFocus();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        RegisterActivity.this.finish();
    }
}
