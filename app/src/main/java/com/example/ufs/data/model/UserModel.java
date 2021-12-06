package com.example.ufs.data.model;

public class UserModel {
    private int id = -1;           // ID for database
    private String email = null;
    private String firstName = null;
    private String lastName = null;
    private String universityID = null; // student's university ID
    private String password = null;
    private boolean isStudent = false;

    // address will collected when student is ordering
    // private String address;

    // Constructors
    public UserModel(String firstName, String lastName, String email, String universityID, String password, boolean isStudent) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.universityID = universityID;
        this.isStudent = isStudent;
        this.password = password;
    }

    // Will be used to return from getUser in the DatabaseHelper
    // to provide to LoggedInUser view model
    //public UserModel(int id, String firstName, boolean isStudent) {
    public UserModel(int id, String firstName, String lastName, String email, String universityID, boolean isStudent) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.universityID = universityID;
        this.isStudent = isStudent;
    }

    // toString - will print out contents of a class object
    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", universityID='" + universityID + '\'' +
                ", isStudent='" + isStudent + '\'' +
                '}';
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String name) { this.firstName = name; }

    public String getLastName() { return lastName; }
    public void setLastName(String name) { this.lastName = name; }

    public String getFullName() { return firstName + " " + lastName; }

    public String getUniversityID() {
        return universityID;
    }
    public void setUniversityID(String universityID) {
        this.universityID = universityID;
    }

    public boolean getIsStudent() { return isStudent; }
    public void setIsStudent(boolean isStudent) { this.isStudent = isStudent; }

    public String getPassword() { return password; }
    public void setIsStudent(String password) { this.password = password; }

    // TODO: methods
    // void OrderFood();
    // void AddFavRestaurant(String restaurantName);
}


