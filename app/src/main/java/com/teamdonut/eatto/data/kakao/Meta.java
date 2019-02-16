package com.teamdonut.eatto.data.kakao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("same_name")
    @Expose
    private SameName sameName;

    @SerializedName("total_count")
    @Expose
    private int totalCount;

    @SerializedName("pageable_count")
    @Expose
    private int pageableCount;

    @SerializedName("is_end")
    @Expose
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
