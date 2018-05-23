package com.example.hvn15.finaleapp;

import com.google.android.gms.maps.model.Marker;

import java.util.Date;

public class Shop {
    private String category;
    private String date;
    private String description;
    private String discount;
    private String period;
    private String priceafter;
    private String pricebefore;
    private String title;
    private String store;
    private Marker marker;

    public Shop(String category, String date, String description, String discount, String period, String priceafter, String pricebefore, String title, String store) {
        this.category = category;
        this.date = date;
        this.description = description;
        this.discount = discount;
        this.period = period;
        this.priceafter = priceafter;
        this.pricebefore = pricebefore;
        this.title = title;
        this.store = store;
    }

    public Shop(String discount, Marker marker) {
        this.discount = discount;
        this.marker = marker;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPriceafter() {
        return priceafter;
    }

    public void setPriceafter(String priceafter) {
        this.priceafter = priceafter;
    }

    public String getPricebefore() {
        return pricebefore;
    }

    public void setPricebefore(String pricebefore) {
        this.pricebefore = pricebefore;
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
        return "Shop{" +
                "category='" + category + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", discount='" + discount + '\'' +
                ", period='" + period + '\'' +
                ", priceafter='" + priceafter + '\'' +
                ", pricebefore='" + pricebefore + '\'' +
                ", title='" + title + '\'' +
                ", store='" + store + '\'' +
                ", marker=" + marker +
                '}';
    }
}
