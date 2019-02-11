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
    private Date appointedTime;

    @SerializedName("max_person")
    private int maxPerson;

    @SerializedName("current_person")
    private int currentPerson;

    @SerializedName("restaurant_name")
    private String restaurantName;

    @SerializedName("writer_id")
    private int writerId;
}
