package com.example.activitytracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainGoalActivity extends AppCompatActivity {

    DatabaseReference dbGoals;
    ListView listViewGoals;

    List<Goal> goalList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_goal);

        //getting from db
        dbGoals = FirebaseDatabase.getInstance().getReference("goals");
        listViewGoals = (ListView) findViewById(R.id.listViewGoals);

        goalList = new ArrayList<>();

        //long press to update
        listViewGoals.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Goal goal = goalList.get(i);

                showGoalUpdateDialog(goal.getGoalId(), goal.getName(), goal.getRoutine(), goal.getStatus(), goal.getFeedback(), goal.getRating());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbGoals.addValueEventListener(new ValueEventListener() {

            //GETTING ALL THE GOALS DATA FROM THE FIREBASE DB
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                goalList.clear(); //because each time this is called, it will get you the entire list

                //read the values from firebase db
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    //getting user goals based on user id
                    if(d.child("userId").getValue(String.class).equals(mAuth.getCurrentUser().getUid())) {
                        Goal goal = d.getValue(Goal.class);
                        goalList.add(goal);
                    }
                }

                GoalList adapter = new GoalList(MainGoalActivity.this, goalList);
                listViewGoals.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void homeAddGoalBtn(View view){
        Intent intent = new Intent(com.example.activitytracker.MainGoalActivity.this, NewGoalActivity.class);
        startActivity(intent);
    }

    //update goal - dialog
    private void showGoalUpdateDialog(String goalId, String goalName, String goalRoutine, String goalStatus, String goalFeedback, float goalRating){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_goal_dialog, null);

        dialogBuilder.setView(dialogView);

        final Spinner name = (Spinner) dialogView.findViewById(R.id.updateGoalName);
        //populate the form by the incoming values
        int selectNameOption=0;
        if(goalName.equals("Walking")){
            selectNameOption = 0;
        }
        else if(goalName.equals("Skipping")){
            selectNameOption = 1;
        }
        else if(goalName.equals("Planks")){
            selectNameOption = 2;
        }
        else if(goalName.equals("PushUps")){
            selectNameOption = 3;
        }
        else if(goalName.equals("PullUps")){
            selectNameOption = 4;
        }
        else if(goalName.equals("SitUps")){
            selectNameOption = 5;
        }
        else if(goalName.equals("Squats")){
            selectNameOption = 6;
        }
        else if(goalName.equals("Meditation")){
            selectNameOption = 7;
        }
        else if(goalName.equals("Yoga")){
            selectNameOption = 8;
        }
        else if(goalName.equals("Drink Water")){
            selectNameOption = 9;
        }

        name.setSelection(selectNameOption);

        final Spinner routine = (Spinner) dialogView.findViewById(R.id.updateGoalRoutine);
        int selectRoutineOption=0;
        if(goalRoutine.equals("Hourly")){
            selectRoutineOption = 0;
        }
        else if(goalRoutine.equals("Daily")){
            selectRoutineOption = 1;
        }
        else if(goalRoutine.equals("Weekly")){
            selectRoutineOption = 2;
        }
        else if(goalRoutine.equals("Monthly")){
            selectRoutineOption = 3;
        }
        routine.setSelection(selectRoutineOption);

        final Spinner status = (Spinner) dialogView.findViewById(R.id.updateGoalStatus);
        int selectStatusOption=0;

        if(goalStatus.equals("Pending")){
            selectStatusOption = 0;
        }
        else if(goalStatus.equals("Completed")){
            selectStatusOption = 1;
        }
        else if(goalStatus.equals("Dropped")){
            selectStatusOption = 2;
        }
        status.setSelection(selectStatusOption);

        final EditText feedback = (EditText) dialogView.findViewById(R.id.updateGoalFeedback);
        //populate the form by the incoming values
        feedback.setText(goalFeedback);

        final RatingBar rating = (RatingBar) dialogView.findViewById(R.id.updateGoalRatingBar);
        //populate the form by the incoming values
        rating.setRating(goalRating);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateGoalBtn);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteExistingGoal);

        dialogBuilder.setTitle("Updating Goal " + goalName + "!");

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goal_name = name.getSelectedItem().toString();
                String goal_routine = routine.getSelectedItem().toString();
                String goal_status = status.getSelectedItem().toString();

                String goal_feedback = feedback.getText().toString().trim();
                float goal_rating = (float) rating.getRating();

                updateGoal(goalId, goal_name, goal_routine, goal_status, goal_feedback, goal_rating);
                alertDialog.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGoal(goalId);
                alertDialog.dismiss();
            }
        });
    }

    //updating in db
    private void deleteGoal(String goalId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("goals").child(goalId);
        databaseReference.removeValue();

        Toast.makeText(this, "Goal Deleted!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(com.example.activitytracker.MainGoalActivity.this, NavBarActivity.class);
        startActivity(intent);
    }

    //deleting in db
    private boolean updateGoal(String goalId, String goalName, String goalRoutine, String goalStatus, String goalFeedback, float goalRating){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("goals").child(goalId);
        //we have referenced the goal using the ID

        Goal goal= new Goal((mAuth.getCurrentUser()).getUid(), goalId, goalName, goalRoutine, goalStatus, goalFeedback, goalRating);

        databaseReference.setValue(goal);

        Toast.makeText(this,"Goal Updated!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(com.example.activitytracker.MainGoalActivity.this, NavBarActivity.class);
        startActivity(intent);

        return true;
    }
}