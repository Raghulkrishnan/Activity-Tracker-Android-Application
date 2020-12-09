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
/*
This class will extend the array adapter and by doing so will be able to get an array list in its constructor
and that list can be used to get a list view using the list xml page.
The IDs on that list xml page can be used to populate each row of the list with the values from the
array list coming in from the constructor
 */
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





