package com.example.activitytracker.model;

public class UserActivity {
    private int id;
    public String activityName;
    public String activityDate;
    private int idUser;
    private String activityFeedback;
    public Unit activityUnit; // Distance - miles (or) Duration - hours and minutes (or) Weight - kilos/pounds (or) Count - N/A
    public String activityValue;
    private String activityRating;

    public UserActivity(){
    }

    public UserActivity(String activityName, String activityDate, Unit activityUnit, String activityValue) {
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.activityUnit = activityUnit;
        this.activityValue = activityValue;
    }

    public String getActivityRating() {
        return activityRating;
    }

    public void setActivityRating(String activityRating) {
        this.activityRating = activityRating;
    }

    public String getActivityFeedback() {
        return activityFeedback;
    }

    public void setActivityFeedback(String activityFeedback) {
        this.activityFeedback = activityFeedback;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityName() { return activityName; }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public Unit getActivityUnit() {
        return activityUnit;
    }

    public void setActivityUnit(Unit activityUnit) {
        this.activityUnit = activityUnit;
    }

    public String getActivityValue() {
        return activityValue;
    }

    public void setActivityValue(String activityValue) {
        this.activityValue = activityValue;
    }

    @Override
    public String toString() {
        return "UserActivity{" +
                "id=" + id +
                ", activityName='" + activityName + '\'' +
                ", activityDate='" + activityDate + '\'' +
                ", idUser=" + idUser +
                ", activityUnit=" + activityUnit +
                ", activityValue=" + activityValue +
                ", activityFeedback=" + activityFeedback +
                ", activityRating=" + activityRating +
                '}';
    }
}
