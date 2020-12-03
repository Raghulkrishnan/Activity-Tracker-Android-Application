package com.example.activitytracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class ActivityList extends ArrayAdapter<UserActivity> {
    private Activity context;
    private List<UserActivity> userActivityList;

    public ActivityList(Activity context, List<UserActivity> userActivityList){
        super(context,R.layout.activity_list_layout,userActivityList);
        this.context = context;
        this.userActivityList = userActivityList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_list_layout, null, true);

        TextView textViewActName = (TextView) listViewItem.findViewById(R.id.textViewActName);
        TextView textViewActDate = (TextView) listViewItem.findViewById(R.id.textViewActDate);
        TextView textViewActValue = (TextView) listViewItem.findViewById(R.id.textViewActValue);

        UserActivity userActivity = userActivityList.get(position);

        textViewActName.setText(userActivity.getName());
        textViewActDate.setText(userActivity.getDate());
        textViewActValue.setText(userActivity.getValue() + " " + userActivity.getType());

        return listViewItem;
    }
}





