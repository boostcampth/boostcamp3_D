package com.teamdonut.eatto.data.kakao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SameName {

    @SerializedName("region")
    private List<String> region;

    @SerializedName("keyword")
    private String keyword;

    @SerializedName("selected_region")
    private String selectedRegion;

    public List<String> getRegion() {
        return region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(String selectedRegion) {
        this.selectedRegion = selectedRegion;
    }
}
