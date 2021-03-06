package com.example.activitytracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
This class will extend the array adapter and by doing so will be able to get an array list in its constructor
and that list can be used to get a list view using the list xml page.
The IDs on the goal list xml page can be used to populate each row of the list with the goal values from the
array list coming in from the constructor
 */
public class GoalList extends ArrayAdapter<Goal> {
    private Activity context;
    private List<Goal> goalList;

    public GoalList(Activity context, List<Goal> goalList){
        super(context,R.layout.goal_list_layout, goalList);
        this.context = context;
        this.goalList = goalList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.goal_list_layout, null, true);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        TextView textViewGoalName= (TextView) listViewItem.findViewById(R.id.textViewGoalName);
        TextView textViewGoalRoutine = (TextView) listViewItem.findViewById(R.id.textViewGoalRoutine);
        TextView textViewGoalStatus = (TextView) listViewItem.findViewById(R.id.textViewGoalStatus);

        Goal goal = goalList.get(position);

        textViewGoalName.setText(goal.getName() + " " + " (" + formattedDate + ")");
        textViewGoalRoutine.setText(goal.getRoutine());
        textViewGoalStatus.setText(goal.getStatus());

        return listViewItem;
    }
}





