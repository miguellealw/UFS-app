package com.example.ufs.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private int userId;
    private String displayName;
    private boolean isStudent;

    //public LoggedInUser(String userId, String displayName) {
    public LoggedInUser(int userId, String displayName, boolean isStudent) {
        this.userId = userId;
        this.displayName = displayName;
        this.isStudent = isStudent;
    }

    public int getUserId() { return userId; }
    public String getDisplayName() { return displayName; }
    public boolean getIsStudent() { return isStudent; }
}