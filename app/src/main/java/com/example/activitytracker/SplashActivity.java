package com.example.activitytracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
/*The SplashActivity class can be used to add a splash screen for the application
by setting the time for the splash screen to appear for five seconds
Once timer is completed, the intent will load your Login Page through the LoginActivity class
 */
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//    setContentView(R.layout.activity_splash);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(in);

                finish();
            }
        }, 5000); //setting the timer for 5 seconds
    }
}
