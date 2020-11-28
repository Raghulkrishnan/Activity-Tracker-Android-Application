package com.example.activitytracker.model;

public class Goals {
    private Type goalType;
    private int value;
    private Status status;
    private Routine routine;

    Goals(){

    }

    public Type getGoalType() {
        return goalType;
    }

    public void setGoalType(Type goalType) {
        this.goalType = goalType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    @Override
    public String toString() {
        return "Goals{" +
                "goalType=" + goalType +
                ", value=" + value +
                ", status=" + status +
                ", routine=" + routine +
                '}';
    }
}
