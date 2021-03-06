package com.example.activitytracker;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
/*
The ProfileActivity class has the information about the user such as, user's first name, last name, email id
along with the link to verify user's email id to validate if the user is using an existing email id
This page also allows you to change your password. By updating the new password, user is automatically logged out of the application
There is a logout button on this page to sign out from the application.
 */
public class ProfileActivity extends AppCompatActivity {
    FirebaseUser user;
    EditText profileFName, profileLName, profileEmail;

    TextView emailVerified;
    //Gif
    ImageView profileGif;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileGif = (ImageView) findViewById((R.id.profileGif));

        //for Gif file  - using Glide Library
        Glide.with(ProfileActivity.this) //activity name
                .load(R.drawable.profile_gif) //GIF url
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(profileGif); //set on image view

        profileFName = (EditText) findViewById(R.id.profileFName);

        profileLName = (EditText) findViewById(R.id.profileLName);

        profileEmail = (EditText) findViewById(R.id.profileEmail);

        emailVerified = (TextView) findViewById(R.id.emailVerified);

        mAuth = FirebaseAuth.getInstance();
        loadUserInformation();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //if user is null
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class ));
        }
    }

    private void loadUserInformation(){
        user = mAuth.getCurrentUser();

        if(user.getDisplayName() != null){
            int idx = user.getDisplayName().lastIndexOf(' ');
            profileFName.setText(user.getDisplayName().substring(0, idx));
            profileLName.setText(user.getDisplayName().substring(idx+1));
        }

        if(user.getEmail() != null){
            profileEmail.setText(user.getEmail());
        }

        if(user.isEmailVerified()){
            emailVerified.setTextColor(Color.GREEN);
            emailVerified.setText("Email Verified");
        }
        else{

            emailVerified.setTextColor(Color.RED);
            emailVerified.setText("Email not verified (click here to verify)");
            emailVerified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Verification Email sent", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    public void saveProfileBtnClick(View view){
        saveUserInformation();
    }

    private void saveUserInformation() {
        //saving user info in firebase
        String fName = profileFName.getText().toString().trim();
        String lName = profileLName.getText().toString().trim();
        String email = profileEmail.getText().toString().trim();

        if (fName.isEmpty()) {
            profileFName.setError("First name required");
            profileFName.requestFocus();
            return;
        }
        else if (lName.isEmpty()) {
            profileLName.setError("Last name required");
            profileLName.requestFocus();
            return;
        }
        else if (email.isEmpty()) {
            profileEmail.setError("Email ID required");
            profileEmail.requestFocus();
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            profileEmail.setError("Enter valid email ID");
            profileEmail.requestFocus();
            return;
        }
        else {
            FirebaseUser user = mAuth.getCurrentUser();
//          saving to firebase
            if(user !=null){
                UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                        .setDisplayName(fName + " " + lName).build();

                user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                            loadUserInformation();

                        }
                    }
                });

                user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Email ID Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Intent intent = new Intent(com.example.activitytracker.ProfileActivity.this, NavBarActivity.class);
                startActivity(intent);
            }
        }
    }

    //changing password
    public void resetPwdBtnClick(View view){
        final EditText resetPassword = new EditText(view.getContext());

        final AlertDialog.Builder pwdResetDialog = new AlertDialog.Builder(view.getContext());

        pwdResetDialog.setTitle("Reset Password?");
        pwdResetDialog.setMessage("Enter new password");
        pwdResetDialog.setView(resetPassword);

        pwdResetDialog.setPositiveButton("YES", (dialogInterface, i) -> {
            String newPwd = resetPassword.getText().toString().trim();

            //firebase auth update password method
            user.updatePassword(newPwd).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Password changed successfully. Login again!", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent intent = new Intent(com.example.activitytracker.ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "FAILED! Password must be minimum 6 characters", Toast.LENGTH_SHORT).show();
                }
            });
        });

        pwdResetDialog.setNegativeButton("NO", (dialogInterface, i) -> {
            //close
        });

        pwdResetDialog.create().show();
    }

    //logout from the application
    public void logoutBtnClick(View view){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(com.example.activitytracker.ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}