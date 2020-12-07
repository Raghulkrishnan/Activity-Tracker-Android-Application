package com.example.activitytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewGoalActivity extends AppCompatActivity {
    Spinner goalName;
    Spinner goalRoutine;
    Spinner goalStatus;

    EditText goalFeedback;
    RatingBar goalRating;

    Button addGoal;

    DatabaseReference dbGoals;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        dbGoals = FirebaseDatabase.getInstance().getReference("goals");

        goalName = (Spinner) findViewById(R.id.goalName);
        goalRoutine = (Spinner) findViewById(R.id.goalRoutine);
        goalStatus = (Spinner) findViewById(R.id.goalStatus);

        goalFeedback = (EditText) findViewById(R.id.goalFeedback);

        goalRating = (RatingBar) findViewById(R.id.goalRatingBar);

        addGoal = (Button) findViewById(R.id.addGoalBtn);

        addGoal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                addGoal();
            }
        });
    }

    //adding in db
    private void addGoal(){
        String name = goalName.getSelectedItem().toString();
        String routine = goalRoutine.getSelectedItem().toString();
        String status = goalStatus.getSelectedItem().toString();

        String feedback = goalFeedback.getText().toString().trim();
        float rating = (float) goalRating.getRating();

        String goalId = dbGoals.push().getKey();

        //user id
        final String UID = (mAuth.getCurrentUser()).getUid();

        //USER ID ADDED
        Goal goal = new Goal(UID, goalId, name, routine, status, feedback, rating);

        dbGoals.child(goalId).setValue(goal);

        Toast.makeText(this, "Goal Added", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(NewGoalActivity.this, NavBarActivity.class);
        startActivity(intent);
    }
}