package com.example.hvn15.finaleapp.objectClasses;

import java.io.Serializable;

public class Person implements Serializable {
    private String role;
    private String username;
    private String password;
    private Double latitude;
    private Double longitude;
    private String title;
    private String address;
    private int maximumDistance;

    public Person(String role, String username, String password, int maximumDistance) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.maximumDistance = maximumDistance;
    }

    public Person(String role, String username, String password, Double latitude, Double longitude, String title, String address) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaximumDistance() {
        return maximumDistance;
    }

    public String getAddress() {
        return address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getRole() {
        return role;
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


    @Override
    public String toString() {
        return "Person{" +
                "role='" + role + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}