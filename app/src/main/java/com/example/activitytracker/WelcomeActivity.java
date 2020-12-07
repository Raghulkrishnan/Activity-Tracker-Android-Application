package com.example.activitytracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends Activity implements View.OnClickListener {

    Button btnActivity;
    Button btnGoal;
    Button btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnActivity = (Button) findViewById(R.id.btnActivity);
        btnGoal = (Button) findViewById(R.id.btnGoal);
        btnProfile = (Button) findViewById(R.id.btnProfile);

        btnActivity.setOnClickListener(this);
        btnGoal.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {
            case R.id.btnActivity:
                i = new Intent(this, MainActivity.class);
                break;
            case R.id.btnGoal:
                i = new Intent(this, MainGoalActivity.class);
                break;
            case R.id.btnProfile:
                i = new Intent(this, ProfileActivity.class);
                break;
        }
        startActivity(i);
        this.finish();
    }
}