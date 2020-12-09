package com.example.activitytracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
/*This RegisterUserActivity has a function to manage the sign up button and to display the activity_register_user xml page
The fields are validated to accept a valid and an existing user from the firebase DB and if password meets the following specifications.
 If the user meets all the requirements, the user is registered successfully
 and allowed to enter the home page of the application
 */
public class RegisterUserActivity extends AppCompatActivity {

    EditText registerFName, registerLName,  registerAge, registerEmail, registerCreatePwd, registerConfirmPwd;
    Spinner registerGender;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        registerFName = (EditText) findViewById(R.id.registerFName);
        registerLName = (EditText) findViewById(R.id.registerLName);
        registerAge = (EditText) findViewById(R.id.registerAge);
        registerEmail = (EditText) findViewById(R.id.registerEmail);
        registerCreatePwd = (EditText) findViewById(R.id.registerCreatePwd);
        registerConfirmPwd = (EditText) findViewById(R.id.registerConfirmPwd);

        registerGender = (Spinner) findViewById(R.id.registerGender);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    public void onCancelClick(View view){
        Intent intent = new Intent(com.example.activitytracker.RegisterUserActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onSignUpClick(View view){

        String fName = registerFName.getText().toString().trim();
        String lName = registerLName.getText().toString().trim();
        String age = registerAge.getText().toString().trim();
        String email = registerEmail.getText().toString().trim();
        String pwd = registerCreatePwd.getText().toString().trim();
        String confirmPwd = registerConfirmPwd.getText().toString().trim();

        //Validations for the registration page
        if(fName.isEmpty()){
            registerFName.setError("First name required");
            registerFName.requestFocus();
            return;
        }
        else if(lName.isEmpty()){
            registerLName.setError("Last name required");
            registerLName.requestFocus();
            return;
        }
        else if(age.isEmpty()){
            registerAge.setError("Age required");
            registerAge.requestFocus();
            return;
        }
        else if(email.isEmpty()){
            registerEmail.setError("Email ID required");
            registerEmail.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            registerEmail.setError("Enter valid email ID");
            registerEmail.requestFocus();
            return;
        }
        else if(pwd.isEmpty()){
            registerCreatePwd.setError("Password required");
            registerCreatePwd.requestFocus();
            return;
        }
        else if(pwd.length() < 6){
            registerCreatePwd.setError("Minimum length of password should be 6");
            registerCreatePwd.requestFocus();
            return;
        }
        else if(confirmPwd.isEmpty()){
            registerConfirmPwd.setError("Confirm Password required");
            registerConfirmPwd.requestFocus();
            return;
        }
        else if(!pwd.equals(confirmPwd)){
            registerConfirmPwd.setError("Passwords should match");
            registerConfirmPwd.requestFocus();
            return;
        }
        else{
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(com.example.activitytracker.RegisterUserActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(getApplicationContext(), "Email ID already registered!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error Occured. Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}