package com.example.activitytracker;

public class UserActivity {
    String userId;
    String activityId;

    String name;
    String date;
    String value;
    String type;
    String feedback;
    float rating;
    String startLocation;
    String endLocation;

    public UserActivity(){

    }

    public UserActivity(String userId, String activityId, String name, String date, String value, String type, String feedback, float rating, String startLocation, String endLocation) {
        this.userId = userId;
        this.activityId = activityId;
        this.name = name;
        this.date = date;
        this.value = value;
        this.type = type;
        this.feedback = feedback;
        this.rating = rating;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    @Override
    public String toString() {
        return "UserActivity{" +
                "userId='" + userId + '\'' +
                ", activityId='" + activityId + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", feedback='" + feedback + '\'' +
                ", rating=" + rating +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                '}';
    }
}
