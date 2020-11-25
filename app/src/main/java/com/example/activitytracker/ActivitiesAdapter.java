package com.example.activitytracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.activitytracker.model.UserActivity;

import java.util.List;

public class ActivitiesAdapter extends BaseAdapter {
    private TextView activityName;
    private TextView activityDate;
    private TextView activityValue;

    private Context mContext;
    private LayoutInflater mInflator;
    private List<UserActivity> mListActivities;

//    private SqlHelper db;

    public ActivitiesAdapter(Context c, List<UserActivity> activities) {
        mContext = c;
        mInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListActivities = activities;
    }

    @Override
    public int getCount() {
        return mListActivities.size();
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

//        db = SqlHelper.getInstance(mContext);

        if(convertView == null) {
            convertView = mInflator.inflate(R.layout.list, parent,  false);
        }

        activityDate = (TextView) convertView.findViewById(R.id.activityDate);
        activityDate.setText(mListActivities.get(position).getActivityDate());

        activityValue = (TextView) convertView.findViewById(R.id.activityValue);
        activityValue.setText(mListActivities.get(position).getActivityValue());

        activityName = (TextView) convertView.findViewById(R.id.activityName);
        activityName.setText(mListActivities.get(position).getActivityName());

        return convertView;
    }
}
