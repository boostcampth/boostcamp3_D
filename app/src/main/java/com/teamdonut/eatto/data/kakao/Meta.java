package com.teamdonut.eatto.data.kakao;

import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("same_name")
    private SameName sameName;

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("pageable_count")
    private int pageableCount;

    @SerializedName("is_end")
    private boolean isEnd;

    public SameName getSameName() {
        return sameName;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPageableCount() {
        return pageableCount;
    }

    public boolean isEnd() {
        return isEnd;
    }
}
