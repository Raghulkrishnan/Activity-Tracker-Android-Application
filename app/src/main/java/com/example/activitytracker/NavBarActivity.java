package com.example.activitytracker;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;

public class NavBarActivity extends TabActivity implements OnCheckedChangeListener{

    private TabHost mTabHost;
    private Intent actIntent;
    private Intent goalIntent;
    private Intent profileIntent;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_nav_bar);

        this.actIntent = new Intent(this,MainActivity.class);
        this.goalIntent = new Intent(this,MainGoalActivity.class);
        this.profileIntent = new Intent(this,ProfileActivity.class);

        ((RadioButton) findViewById(R.id.radioBtnActivity)).setOnCheckedChangeListener(this);

        ((RadioButton) findViewById(R.id.radioBtnGoal)).setOnCheckedChangeListener(this);

        ((RadioButton) findViewById(R.id.radioBtnProfile)).setOnCheckedChangeListener(this);

        setupIntent();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            switch (buttonView.getId()) {
                case R.id.radioBtnActivity:
                    this.mTabHost.setCurrentTabByTag("A_TAB");
                    break;
                case R.id.radioBtnGoal:
                    this.mTabHost.setCurrentTabByTag("B_TAB");
                    break;
                case R.id.radioBtnProfile:
                    this.mTabHost.setCurrentTabByTag("C_TAB");
                    break;
            }
        }

    }

    private void setupIntent() {
        this.mTabHost = getTabHost();
        TabHost localTabHost = this.mTabHost;

        localTabHost.addTab(buildTabSpec("A_TAB", R.string.main_home, R.drawable.icon_1_n, this.actIntent));

        localTabHost.addTab(buildTabSpec("B_TAB", R.string.main_goal, R.drawable.icon_2_n, this.goalIntent));

        localTabHost.addTab(buildTabSpec("C_TAB", R.string.main_profile, R.drawable.icon_3_n, this.profileIntent));
    }

    private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon, final Intent content) {
        return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel), getResources().getDrawable(resIcon)).setContent(content);
    }
}