package com.example.activitytracker;

import java.util.List;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private String username;
    private String password;

    private List<UserActivity> userActivityList;

    public User(){

    }

    public User(String userId, String firstName, String lastName, String gender, int age, String username, String password, List<UserActivity> userActivityList) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.username = username;
        this.password = password;
        this.userActivityList = userActivityList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserActivity> getUserActivityList() {
        return userActivityList;
    }

    public void setUserActivityList(List<UserActivity> userActivityList) {
        this.userActivityList = userActivityList;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userActivityList=" + userActivityList +
                '}';
    }
}
