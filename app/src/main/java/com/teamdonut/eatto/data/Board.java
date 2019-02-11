package com.teamdonut.eatto.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Board {

    public Board(String title, String address, String appointed_time, String restaurant_name, int max_person, int min_age, int max_age, int budget,
                 String content, double longitude, double latitude, int writer_id) {
        this.title = title;
        this.address = address;
        this.appointed_time = appointed_time;
        this.restaurant_name = restaurant_name;
        this.max_person = max_person;
        this.min_age = min_age;
        this.max_age = max_age;
        this.budget = budget;
        this.content = content;
        this.longitude = longitude;
        this.latitude = latitude;
        this.writer_id = writer_id;
    }

    @SerializedName("id")
    private int id;

    @SerializedName("address")
    private String address;

    @SerializedName("title")
    private String title;

    @SerializedName("appointed_time")
    private String appointed_time;

    @SerializedName("max_person")
    private int max_person;

    @SerializedName("current_person")
    private int current_person;

    @SerializedName("restaurant_name")
    private String restaurant_name;

    @SerializedName("writer_id")
    private int writer_id;

    @SerializedName("write_date")
    private String write_date;

    @SerializedName("validation")
    private int validation;

    @SerializedName("content")
    private String content;

    @SerializedName("expire_date")
    private String expire_date;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("budget")
    private int budget;

    @SerializedName("min_age")
    private int min_age;

    @SerializedName("max_age")
    private int max_age;


    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getTitle() {
        return title;
    }

    public String getAppointed_time() {
        return appointed_time;
    }

    public int getMax_person() {
        return max_person;
    }

    public int getCurrent_person() {
        return current_person;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public int getWriter_id() {
        return writer_id;
    }

    public String getWrite_date() {
        return write_date;
    }

    public int getValidation() {
        return validation;
    }

    public String getContent() {
        return content;
    }

    public String getExpire_date() {
        return expire_date;
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

    public int getMin_age() {
        return min_age;
    }

    public int getMax_age() {
        return max_age;
    }
}
