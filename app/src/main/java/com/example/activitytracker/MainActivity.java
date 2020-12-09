package com.example.activitytracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
The Glide library is used to display the gif at the top of the page using the ImageView.
The activity list is displayed on the page and a Add New Activity button is present which when clicked will open the NewUserActivity page where the user
can add the details of the new activity to be logged in.
This page also has a feature that will allow the user to long press on any of the activities in the list to open a dialog box in which
the user can edit or delete the activity from their list.
 */
public class MainActivity extends AppCompatActivity {
    DatabaseReference dbActivities;
    ListView listViewActivities;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    List<UserActivity> userActivityList;

    //Gif
    ImageView actGif;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        actGif = (ImageView) findViewById((R.id.actGif));
        //for Gif file  - using Glide Library
        Glide.with(MainActivity.this) //activity name
                .load(R.drawable.act_gif) //GIF url
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(actGif); //set on image view

        //getting from db
        dbActivities = FirebaseDatabase.getInstance().getReference("activities");
        listViewActivities = (ListView) findViewById(R.id.listViewActivities);

        userActivityList = new ArrayList<>();

        //long press to update
        listViewActivities.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserActivity userActivity = userActivityList.get(i);

                showUpdateDialog(userActivity.getActivityId(), userActivity.getName(), userActivity.getDate(), userActivity.getType(), userActivity.getValue(), userActivity.getFeedback(), userActivity.getRating(), userActivity.getStartLocation(), userActivity.getEndLocation());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbActivities.addValueEventListener(new ValueEventListener() {
            //GETTING ALL THE ACTIVITIES DATA FROM THE FIREBASE DB
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(mAuth.getCurrentUser().getUid());
                userActivityList.clear(); //because each time this is called, it will get you the entire list

                //read the values from firebase db
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    //getting user activities based on user id
                    if(d.child("userId").getValue(String.class).equals(mAuth.getCurrentUser().getUid())){
                        UserActivity userActivity = d.getValue(UserActivity.class);
                        userActivityList.add(userActivity);
                    }

                }
                ActivityList adapter = new ActivityList(MainActivity.this, userActivityList);
                listViewActivities.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void homeAddActivityBtn(View view){
        Intent intent = new Intent(com.example.activitytracker.MainActivity.this, NewUserActivity.class);
        startActivity(intent);
    }

    //update activity - dialog
    private void showUpdateDialog(String actId, String actName, String actDate, String actType, String actValue, String actFeedback, float actRating, String actStartLocation, String actEndLocation){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_activity_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText name = (EditText) dialogView.findViewById(R.id.updateActName);
        //populate the form by the incoming values
        name.setText(actName);
        final EditText date = (EditText) dialogView.findViewById(R.id.updateActDate);
        //populate the form by the incoming values
        date.setText(actDate);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Black,
                        onDateSetListener,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month +=1; //since here month ranges from 0-11. 0 in JAN, 11 is DECEMBER.
                String d = month + "/" + day + "/" + year;
                date.setText(d);
            }
        };

        final Spinner type = (Spinner) dialogView.findViewById(R.id.updateActType);
        //populate the form by the incoming values
        int selectionNumber=0;
        if(actType.equals("Miles")){
            selectionNumber = 0;
        }
        else if(actType.equals("Pounds")){
            selectionNumber = 1;
        }
        else if(actType.equals("Minutes")){
            selectionNumber = 2;
        }
        else if(actType.equals("Count")){
            selectionNumber = 3;
        }
        else if(actType.equals("Steps")){
            selectionNumber = 4;
        }
        else if(actType.equals("Days")){
            selectionNumber = 5;
        }

        type.setSelection(selectionNumber);

        final EditText value = (EditText) dialogView.findViewById(R.id.updateActValue);
        //populate the form by the incoming values
        value.setText(actValue);

        final EditText feedback = (EditText) dialogView.findViewById(R.id.updateActFeedback);
        //populate the form by the incoming values
        feedback.setText(actFeedback);

        final EditText startLocation = (EditText) dialogView.findViewById(R.id.updateStartLocation);
        //populate the form by the incoming values
        startLocation.setText(actStartLocation);

        final EditText endLocation = (EditText) dialogView.findViewById(R.id.updateEndLocation);
        //populate the form by the incoming values
        endLocation.setText(actEndLocation);

        final RatingBar rating = (RatingBar) dialogView.findViewById(R.id.updateActRating);
        //populate the form by the incoming values
        rating.setRating(actRating);

        final Button buttonViewLocations = (Button) dialogView.findViewById(R.id.updateViewLocations);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateExistingAct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteExistingAct);

        dialogBuilder.setTitle("Updating Activity " + actName + "!");

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        //MAP
        buttonViewLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.activitytracker.MainActivity.this, MapActivity.class);
                String startLoc = actStartLocation;
                String endLoc = actEndLocation;

                Bundle bundle = new Bundle();
                bundle.putString("start", startLoc);
                bundle.putString("end", endLoc);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updActName = name.getText().toString().trim();
                String updActDate = date.getText().toString().trim();
                String updActValue = value.getText().toString().trim();

                String updActType = type.getSelectedItem().toString();

                String updActFeedback = feedback.getText().toString().trim();

                String updStartLoc = startLocation.getText().toString().trim();
                String updAEndLoc = endLocation.getText().toString().trim();

                float updActRating = (float) rating.getRating();

                if(TextUtils.isEmpty(updActName)){
                    name.setError("Name required");
                    name.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(updActDate)){
                    date.setError("Date required");
                    date.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(updActValue)) {
                    value.setError("Value required");
                    value.requestFocus();
                    return;
                }
                else{
                    updateActivity(actId, updActName, updActDate, updActType, updActValue, updActFeedback, updActRating, updStartLoc, updAEndLoc);
                    alertDialog.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteActivity(actId);
                alertDialog.dismiss();
            }
        });
    }

    //deleting in db
    private void deleteActivity(String actId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("activities").child(actId);
        databaseReference.removeValue();

        Toast.makeText(this, "Activity Deleted!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(com.example.activitytracker.MainActivity.this, NavBarActivity.class);
        startActivity(intent);
    }

    //updating in db
    private boolean updateActivity(String actId, String actName, String actDate, String actType, String actValue, String actFeedback, float actRating, String actStartLocation, String actEndLocation){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("activities").child(actId); // ID of the activity that is to be updated
        //we have referenced the activity using the ID

        //USER ID ADDED
        UserActivity userActivity = new UserActivity((mAuth.getCurrentUser()).getUid(), actId, actName, actDate, actValue, actType, actFeedback, actRating, actStartLocation, actEndLocation);

        databaseReference.setValue(userActivity);

        Toast.makeText(this,"Activity Updated!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(com.example.activitytracker.MainActivity.this, NavBarActivity.class);
        startActivity(intent);

        return true;
    }

}








