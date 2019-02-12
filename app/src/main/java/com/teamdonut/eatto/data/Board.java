package com.teamdonut.eatto.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Board {



    @SerializedName("id")
    private int id;

    @SerializedName("address")
    private String address;

    @SerializedName("title")
    private String title;

    @SerializedName("appointed_time")
    private String appointedTime;

    @SerializedName("max_person")
    private int maxPerson;

    @SerializedName("current_person")
    private int currentPerson;

    @SerializedName("restaurant_name")
    private String restaurantName;

    @SerializedName("writer_id")
    private int writerId;

    @SerializedName("write_date")
    private String writeDate;

    @SerializedName("validation")
    private int validation;

    @SerializedName("content")
    private String content;

    @SerializedName("expire_date")
    private String expireDate;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("budget")
    private int budget;

    @SerializedName("min_age")
    private int minAge;

    @SerializedName("max_age")
    private int maxAge;

    public Board(String title, String address, String appointed_time, String restaurant_name, int max_person, int min_age, int max_age, int budget,
                 String content, double longitude, double latitude, int writer_id) {
        this.title = title;
        this.address = address;
        this.appointedTime = appointed_time;
        this.restaurantName = restaurant_name;
        this.maxPerson = max_person;
        this.minAge = min_age;
        this.maxAge = max_age;
        this.budget = budget;
        this.content = content;
        this.longitude = longitude;
        this.latitude = latitude;
        this.writerId = writer_id;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getTitle() {
        return title;
    }

    public String getAppointedTime() {
        return appointedTime;
    }

    public int getMaxPerson() {
        return maxPerson;
    }

    public int getCurrentPerson() {
        return currentPerson;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getWriterId() {
        return writerId;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public int getValidation() {
        return validation;
    }

    public String getContent() {
        return content;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getBudget() {
        return budget;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
