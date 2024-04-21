package com.dmiiy.wayapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MailLogin extends AppCompatActivity implements View.OnClickListener{
private TextView backsignin,forgetpassword;
private MaterialEditText emaillogin,passwordlogin;
private Button login;
private ProgressBar progressBarlogin;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_login);
//        backsignin = (TextView) findViewById(R.id.textgotoreg);
//        backsignin.setOnClickListener(this);
        forgetpassword = (TextView) findViewById(R.id.textmailforgetpas);
        forgetpassword.setOnClickListener(this);
        emaillogin= (MaterialEditText) findViewById(R.id.Emailemaillogin);
        passwordlogin= (MaterialEditText) findViewById(R.id.Passwordemailsignin);
        login = (Button) findViewById(R.id.buttonsignin);
        login.setOnClickListener(this);
        progressBarlogin = (ProgressBar) findViewById(R.id.progressBarEmaillogin);
        mAuth=FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonsignin: {
                userLogin();
                break;
            }
            case R.id.textmailforgetpas:{
                startActivity(new Intent(this, com.dmiiy.wayapp.forgetpassword.class));
                break;
            }
        }
    }

    private void userLogin() {
        String email = emaillogin.getText().toString().trim();
        String password = passwordlogin.getText().toString().trim();

        if (email.isEmpty()){
            emaillogin.setError("Почта не указана!");
            emaillogin.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emaillogin.setError("Пожалуйста укажите верную почту!");
            emaillogin.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordlogin.setError("Пароль не указан!");
            passwordlogin.requestFocus();
            return;
        }
        if (password.length()<6){
            passwordlogin.setError("Пароль не может быть <6 !");
            passwordlogin.requestFocus();
            return;
        }
        progressBarlogin.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                    startActivity(new Intent(MailLogin.this, Welcome.class));
                    finish();
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(MailLogin.this,"Проверьте свою почту",Toast.LENGTH_LONG).show();
                        progressBarlogin.setVisibility(View.GONE);
                    }
                }
                else{
                    Toast.makeText(MailLogin.this,"Ошибка входа",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}