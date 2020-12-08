package com.example.activitytracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.net.Inet4Address;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail, loginPwd;
    ProgressBar loginProgressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPwd = (EditText) findViewById(R.id.loginPwd);
        loginProgressBar = (ProgressBar) findViewById(R.id.loginProgressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    public void loginOnSignInClick(View view){

        String email = loginEmail.getText().toString().trim();
        String pwd = loginPwd.getText().toString().trim();

        if(email.isEmpty()){
            loginEmail.setError("Enter Email ID");
            loginEmail.requestFocus();
            return;
        }
        else if(pwd.isEmpty()){
            loginPwd.setError("Enter Password");
            loginPwd.requestFocus();
            return;
        }
        else{
            loginProgressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
//                        finish(); // cannot come back to login page by clicking back button
                        loginProgressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(com.example.activitytracker.LoginActivity.this, NavBarActivity.class);
                      //when clicked back after logging in, page should not come out of the app.so using addFlags method
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        loginProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //checking if the user is already logged in - then send him in directly.
    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(this, NavBarActivity.class ));
        }
    }

    public void loginOnSignUpClick(View view){
        Intent intent = new Intent(com.example.activitytracker.LoginActivity.this, RegisterUserActivity.class);
        startActivity(intent);
    }

}