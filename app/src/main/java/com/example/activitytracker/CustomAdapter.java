package com.example.activitytracker;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.activitytracker.model.UserActivity;

import java.util.List;

public class CustomAdapter extends BaseAdapter{
    private LayoutInflater lInflater;
    private List<UserActivity> listStorage;

    public CustomAdapter(Context context, List<UserActivity>  customizedListView) {
        lInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    private static class ViewHolder {
        TextView aName;
        TextView aDate;
        TextView aValue;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();
//            lInflater = LayoutInflater.from(getContext());
            convertView = lInflater.inflate(R.layout.list, parent,false);

            viewHolder.aName = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.aDate = (TextView) convertView.findViewById(R.id.textView2);
            viewHolder.aValue = (TextView) convertView.findViewById(R.id.textView3);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder); //memory agent
        }
        else{
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();  //recovery agent

        }
        // into the template view.
        viewHolder.aName.setText(listStorage.get(position).getActivityName());
        viewHolder.aDate.setText(listStorage.get(position).getActivityDate());
        viewHolder.aValue.setText(listStorage.get(position).getActivityValue() + " " + listStorage.get(position).getActivityUnit() );

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.GRAY);
        } else {
            convertView.setBackgroundColor(Color.DKGRAY);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
