package com.example.activitytracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    int invalidCount = 0;
//    private SqlHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        db = SqlHelper.getInstance(getApplicationContext());
    }
    public void onRegisterBtnClick(View view){
//        Intent intent = new Intent(com.example.activitytracker.LoginActivity.this, RegisterActivity.class);
//        startActivity(intent);
    }

    public void onLoginBtnClick(View view){
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        String user = username.getText().toString();
        invalidCount++;

        if(user.equals("rbala1")){ //USERNAME - should be matched to the db value
            if (password.getText().toString().equals("raghul123")) { // PASSWORD - should be matched to the db value
                String text = "Redirecting... to ACT main screen";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
                Intent intent = new Intent(com.example.activitytracker.LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        else{
            if(invalidCount == 1) {
                String text = "Please check your username or password.\n" +  " Number of attempts left: 2";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }
            else if(invalidCount == 2){
                String text = "Please check your username or password.\n" +  "Number of attempts left: 1";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }
            else{
                String text = "Limit exceeded. Restart app to continue.";
                Button button = findViewById(R.id.loginBtn);
                int duration = Toast.LENGTH_SHORT;
                //disabling the button so that when limit is exceeded user cannot login.
                button.setEnabled(false);
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }
        }
    }
}