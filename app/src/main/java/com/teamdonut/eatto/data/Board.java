package com.teamdonut.eatto.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

public class Board implements Parcelable, ClusterItem {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("appointed_time")
    @Expose
    private String appointedTime;

    @SerializedName("max_person")
    @Expose
    private int maxPerson;

    @SerializedName("current_person")
    @Expose
    private int currentPerson;

    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;

    @SerializedName("writer_id")
    @Expose
    private long writerId;

    @SerializedName("write_date")
    @Expose
    private String writeDate;

    @SerializedName("validation")
    @Expose
    private int validation;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("expire_date")
    @Expose
    private String expireDate;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("budget")
    @Expose
    private String budget;

    @SerializedName("min_age")
    @Expose
    private int minAge;

    @SerializedName("max_age")
    @Expose
    private int maxAge;

    @SerializedName("writer_photo")
    @Expose
    private String writerPhoto;

    @SerializedName("writer_name")
    @Expose
    private String writerName;

    public Board(String title, String address, String appointed_time, String restaurant_name,
                 int max_person, int min_age, int max_age, double longitude, double latitude, long writer_id, String writerPhoto) {
        this.title = title;
        this.address = address;
        this.appointedTime = appointed_time;
        this.restaurantName = restaurant_name;
        this.maxPerson = max_person;
        this.minAge = min_age;
        this.maxAge = max_age;
        this.longitude = longitude;
        this.latitude = latitude;
        this.writerId = writer_id;
        this.writerPhoto = writerPhoto;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(getLatitude(), getLongitude());
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return address;
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

    public long getWriterId() {
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

    public String getBudget() {
        return budget;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAppointedTime(String appointedTime) {
        this.appointedTime = appointedTime;
    }

    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }

    public void setCurrentPerson(int currentPerson) {
        this.currentPerson = currentPerson;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setWriterId(int writerId) {
        this.writerId = writerId;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public void setValidation(int validation) {
        this.validation = validation;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }


    protected Board(Parcel in) {
        this.title = in.readString();
        this.writerPhoto = in.readString();
        this.address = in.readString();
        this.appointedTime = in.readString();
        this.restaurantName = in.readString();
        this.maxPerson = in.readInt();
        this.minAge = in.readInt();
        this.maxAge = in.readInt();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.writerId = in.readLong();
    }

    public static final Parcelable.Creator<Board> CREATOR =
            new Parcelable.Creator<Board>() {
                @Override
                public Board createFromParcel(Parcel in) {
                    return new Board(in);
                }

                @Override
                public Board[] newArray(int size) {
                    return new Board[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(writerPhoto);
        dest.writeString(title);
        dest.writeString(address);
        dest.writeString(appointedTime);
        dest.writeString(restaurantName);
        dest.writeInt(minAge);
        dest.writeInt(maxAge);
        dest.writeInt(maxPerson);
        dest.writeString(budget);
        dest.writeString(content);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public String getWriterPhoto() {
        return writerPhoto;
    }

    public String getWriterName() {
        return writerName;
    }
}
