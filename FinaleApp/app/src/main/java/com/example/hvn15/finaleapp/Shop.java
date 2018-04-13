package com.example.hvn15.finaleapp;

public class Shop {
    private String title;
    private String description;

    public Shop(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Person{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
