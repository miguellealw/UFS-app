package com.example.ufs.data.model;

public class StudentModel {
    private int id;           // ID for database
    private String studentID; // student ID
    private String username;
    private String name;
    private String address;


    // Constructors
    public StudentModel(int id, String name, String address, String studentID, String username) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.studentID = studentID;
        this.username = username;
    }

    // toString - will print out contents of a class object
    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", studentID='" + studentID + '\'' +
                ", username='" + username + '\'' +
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String username() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // TODO: methods
    // void OrderFood();
    // void AddFavRestaurant(String restaurantName);
}
