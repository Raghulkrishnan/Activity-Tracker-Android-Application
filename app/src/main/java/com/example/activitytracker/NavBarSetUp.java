package com.example.activitytracker;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;

public class NavBarSetUp extends TabActivity implements CompoundButton.OnCheckedChangeListener {

    private TabHost navBar;
    private Intent activityPage;
    private Intent goalPage;
    private Intent profilePage;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_navbar_home);

       // this.activityPage = new Intent(this,HomeActivity.class);
//        this.goalPage = new Intent(this,GoalActivity.class);
//        this.profilePage = new Intent(this,ProfileActivity.class);

        ((RadioButton) findViewById(R.id.goToHomeBtn))
                .setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.goToGoalsBtn))
                .setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.goToProfileBtn))
                .setOnCheckedChangeListener(this);

        setupIntent();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            switch (buttonView.getId()) {
                case R.id.goToHomeBtn:
                    this.navBar.setCurrentTabByTag("home");
                    break;
                case R.id.goToGoalsBtn:
                    this.navBar.setCurrentTabByTag("goals");
                    break;
                case R.id.goToProfileBtn:
                    this.navBar.setCurrentTabByTag("profile");
                    break;
            }
        }

    }

    private void setupIntent() {
        this.navBar = getTabHost();
        TabHost localTabHost = this.navBar;

//        localTabHost.addTab(buildTabSpec("home", R.string.main_home, R.drawable.home_icon, this.activityPage));
//
//        localTabHost.addTab(buildTabSpec("goals", R.string.main_goals, R.drawable.goals_icon, this.goalPage));
//
//        localTabHost.addTab(buildTabSpec("profile", R.string.main_profile, R.drawable.profile_icon, this.profilePage));
    }

    private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon, final Intent content) {
        return this.navBar.newTabSpec(tag).setIndicator(getString(resLabel), getResources().getDrawable(resIcon)).setContent(content);
    }
}