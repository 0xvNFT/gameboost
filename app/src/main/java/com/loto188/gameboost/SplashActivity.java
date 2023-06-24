package com.loto188.gameboost;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 2000; // Splash screen timeout in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(android.R.style.Theme_DeviceDefault_Light);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delay launching the main activity by the specified timeout
        new Handler().postDelayed(() -> {
            // Start the main activity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

            // Close the splash activity
            finish();
        }, SPLASH_TIMEOUT);
    }
}
