package com.example.activitytracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

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

        TextView textViewGoalName= (TextView) listViewItem.findViewById(R.id.textViewGoalName);
        TextView textViewGoalRoutine = (TextView) listViewItem.findViewById(R.id.textViewGoalRoutine);
        TextView textViewGoalStatus = (TextView) listViewItem.findViewById(R.id.textViewGoalStatus);

        Goal goal = goalList.get(position);

        textViewGoalName.setText(goal.getName());
        textViewGoalRoutine.setText(goal.getRoutine());
        textViewGoalStatus.setText(goal.getStatus());

        return listViewItem;
    }
}





