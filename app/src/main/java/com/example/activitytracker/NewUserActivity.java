package com.example.activitytracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Provider;
import java.util.Calendar;
/* The NewUserActivity page has a function to manage the add new activity button and to display the activity_main xml page
The user can enter all of the activity details such as activity name, date, value, feedback, start and end location of the activity along with the rating
The add activity button on this page adds all of the fields entered to the database
 */
public class NewUserActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    EditText actName;
    TextView actDate;
    EditText actValue;
    EditText actFeedback;
    EditText actStartLoc;
    EditText actEndLoc;

    Spinner actType;
    RatingBar actRating;

    Button addActivity;

    DatabaseReference dbActivities;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbActivities = FirebaseDatabase.getInstance().getReference("activities");

        actName = (EditText) findViewById(R.id.editTextActName);
        actDate = (TextView) findViewById(R.id.activityDate);
        actDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewUserActivity.this,
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
                String date = month + "/" + day + "/" + year;
                actDate.setText(date);
            }
        };

        actValue = (EditText) findViewById(R.id.editTextActValue);
        actFeedback = (EditText) findViewById(R.id.editTextActFeedback);
        actStartLoc = (EditText) findViewById(R.id.editTextActStartLoc);
        actEndLoc = (EditText) findViewById(R.id.editTextActEndLoc);

        actRating = (RatingBar) findViewById(R.id.actRatingBar);

//        addLocations = (Button) findViewById(R.id.actAddLocations);
        addActivity = (Button) findViewById(R.id.actAddActivity);
        actType = (Spinner) findViewById(R.id.activityType);

        addActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                addActivity();
            }
        });

//        LOCATIONS
//        addLocations.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(com.example.track_act.NewUserActivity.this, MapActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    //adding in db
    private void addActivity(){
        String name = actName.getText().toString().trim();
        String date = actDate.getText().toString().trim();
        String value = actValue.getText().toString().trim();

        String type = actType.getSelectedItem().toString();

        String feedback = actFeedback.getText().toString().trim();
        String startLocation = actStartLoc.getText().toString().trim();
        String endLocation = actEndLoc.getText().toString().trim();
        float rating = (float) actRating.getRating();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter activity name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(date)){
            Toast.makeText(this, "Enter activity date", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(type)){
            Toast.makeText(this, "Enter activity type", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(value)) {
            Toast.makeText(this, "Enter activity value", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            String id = dbActivities.push().getKey(); //every time you add an activity an unique id will be generated here. so it wont overwrite existing activity

            //user id
            final String UID = (mAuth.getCurrentUser()).getUid();

            //USER ID ADDED
            UserActivity userActivity = new UserActivity(UID, id, name,date,value,type,feedback,rating,startLocation,endLocation);

            dbActivities.child(id).setValue(userActivity); //setting the activity in the activity tree inside the db - thats why - db.child(id).set..

            Toast.makeText(this, "Activity Added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(com.example.activitytracker.NewUserActivity.this, NavBarActivity.class);
            startActivity(intent);
        }

    }
}








