package com.example.activitytracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        populating the age dropdown
        List age = new ArrayList<Integer>();
        for (int i = 6; i <= 99; i++) {
            age.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        Spinner spinner = (Spinner)findViewById(R.id.ageSpinner);
        spinner.setAdapter(spinnerArrayAdapter);
    }
    public void onBackToLoginBtnClick(View view){
        Intent intent = new Intent(com.example.activitytracker.RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onRegisterBtnClick(View view){
        Intent intent = new Intent(com.example.activitytracker.RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}