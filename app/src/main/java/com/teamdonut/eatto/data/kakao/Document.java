package com.teamdonut.eatto.data.kakao;

import com.google.gson.annotations.SerializedName;

public class Document {
    @SerializedName("id")
    private String id;
    @SerializedName("place_name")
    private String placeName;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("category_group_code")
    private String categoryGroupCode;
    @SerializedName("category_group_name")
    private String categoryGroupName;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address_name")
    private String addressName;
    @SerializedName("road_address_name")
    private String roadAddressName;
    @SerializedName("x")
    private String x;
    @SerializedName("y")
    private String y;
    @SerializedName("place_url")
    private String placeUrl;
    @SerializedName("distance")
    private String distance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryGroupCode() {
        return categoryGroupCode;
    }

    public void setCategoryGroupCode(String categoryGroupCode) {
        this.categoryGroupCode = categoryGroupCode;
    }

    public String getCategoryGroupName() {
        return categoryGroupName;
    }

    public void setCategoryGroupName(String categoryGroupName) {
        this.categoryGroupName = categoryGroupName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getRoadAddressName() {
        return roadAddressName;
    }

    public void setRoadAddressName(String roadAddressName) {
        this.roadAddressName = roadAddressName;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public void setPlaceUrl(String placeUrl) {
        this.placeUrl = placeUrl;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
