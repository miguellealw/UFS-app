package com.example.ufs.data.model;

public class StudentModel {
    private int id;           // ID for database
    private String universityID; // student's university ID
    private String name;

    // address will collected when student is ordering
    // private String address;

    // Constructors
    public StudentModel(int id, String name, String universityID) {
        this.id = id;
        this.name = name;
        this.universityID = universityID;
    }

    // toString - will print out contents of a class object
    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", universityID='" + universityID + '\'' +
                '}';
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUniversityID() {
        return universityID;
    }
    public void setUniversityID(String universityID) {
        this.universityID = universityID;
    }

    // TODO: methods
    // void OrderFood();
    // void AddFavRestaurant(String restaurantName);
}
