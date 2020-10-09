package com.example.travelnepalapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.travelnepalapp.R;
import com.example.travelnepalapp.Users.LoginSignup;

public class SplashScreen extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences preferences = getSharedPreferences("localstorage", 0);
        if (preferences.getBoolean("loginchecker", true)) {

            intent = new Intent(SplashScreen.this, LoginSignup.class);

        } else {
            intent = new Intent(SplashScreen.this, LoginSignup.class);

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}
