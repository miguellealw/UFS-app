package com.example.ufs.data.model;

public class UserModel {
    private int id;           // ID for database
    private String email;
    private String name;
    private String universityID; // student's university ID
    private int isStudent;

    // address will collected when student is ordering
    // private String address;

    // Constructors
    public UserModel(int id, String email, String name, String universityID, int isStudent) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.universityID = universityID;
        this.isStudent = isStudent;
    }

    // toString - will print out contents of a class object
    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUniversityID() {
        return universityID;
    }
    public void setUniversityID(String universityID) {
        this.universityID = universityID;
    }

    public int getIsStudent() { return isStudent; }
    public void setIsStudent(int isStudent) { this.isStudent = isStudent; }

    // TODO: methods
    // void OrderFood();
    // void AddFavRestaurant(String restaurantName);
}


