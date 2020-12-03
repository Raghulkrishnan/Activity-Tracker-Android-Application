package com.example.activitytracker;

public class Goal {
    private String userId;
    private String goalId;
    private String name;
    private String routine;
    private String status;
    private String feedback;
    private float rating;

    public Goal(){
    }

    public Goal(String userId, String goalId, String name, String routine, String status, String feedback, float rating) {
        this.userId = userId;
        this.goalId = goalId;
        this.name = name;
        this.routine = routine;
        this.status = status;
        this.feedback = feedback;
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Goal{" +
                "userId='" + userId + '\'' +
                ", goalId='" + goalId + '\'' +
                ", name='" + name + '\'' +
                ", routine='" + routine + '\'' +
                ", status='" + status + '\'' +
                ", feedback='" + feedback + '\'' +
                ", rating=" + rating +
                '}';
    }
}
