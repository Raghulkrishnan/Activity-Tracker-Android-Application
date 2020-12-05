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
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference dbActivities;
    ListView listViewActivities;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    List<UserActivity> userActivityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

                userActivityList.clear(); //because each time this is called, it will get you the entire list

                //read the values from firebase db
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    UserActivity userActivity = d.getValue(UserActivity.class);
                    userActivityList.add(userActivity);
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
        final EditText date = (EditText) dialogView.findViewById(R.id.updateActDate);

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
        final EditText value = (EditText) dialogView.findViewById(R.id.updateActValue);
        final EditText feedback = (EditText) dialogView.findViewById(R.id.updateActFeedback);

        final RatingBar rating = (RatingBar) dialogView.findViewById(R.id.updateActRating);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateExistingAct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteExistingAct);

        dialogBuilder.setTitle("Updating Activity " + actName + "!");

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String actName = name.getText().toString().trim();
                String actDate = date.getText().toString().trim();
                String actValue = value.getText().toString().trim();

                String actType = type.getSelectedItem().toString();

                String actFeedback = feedback.getText().toString().trim();
                float actRating = (float) rating.getRating();

                if(TextUtils.isEmpty(actName)){
                    name.setError("Name required");
                    name.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(actDate)){
                    date.setError("Date required");
                    date.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(actValue)) {
                    value.setError("Value required");
                    value.requestFocus();
                    return;
                }
                else{
                    updateActivity(actId, actName, actDate, actType, actValue, actFeedback, actRating, actStartLocation, actEndLocation);
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
    }

    //updating in db
    private boolean updateActivity(String actId, String actName, String actDate, String actType, String actValue, String actFeedback, float actRating, String actStartLocation, String actEndLocation){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("activities").child(actId); // ID of the activity that is to be updated
        //we have referenced the activity using the ID

        UserActivity userActivity = new UserActivity("1", actId, actName, actDate, actValue, actType, actFeedback, actRating, actStartLocation, actEndLocation);

        databaseReference.setValue(userActivity);

        Toast.makeText(this,"Activity Updated!", Toast.LENGTH_SHORT).show();

        return true;
    }
}








