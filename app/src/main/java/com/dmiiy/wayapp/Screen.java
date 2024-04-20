package com.dmiiy.wayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Screen extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
private Handler handler =new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            int c=buttonCheckAirplaneMode();
            if (c==0) {
                if (isConnected()){
                if (!isFinishing()) {
                    if (user != null) {
                        Toast.makeText(Screen.this, "Добро пожаловать !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(Screen.this, "Добро пожаловать !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MailLogin.class));
                        finish();
                    }
                }} else {
                    Toast.makeText(Screen.this, "Нет подключения к интернету ! Проверьте связь !", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(Screen.this, "Включен режим полёта ! Для работы приложения выключите его !", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable,500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
    public int buttonCheckAirplaneMode (){
        if (Settings.Global.getInt(this.getContentResolver(),Settings.Global.AIRPLANE_MODE_ON,0)!=0){
            return 1;
        }else{
            return 0;
        }

    }
    boolean isConnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null){
            if (networkInfo.isConnected()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}