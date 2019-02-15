package com.teamdonut.eatto.data.kakao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocalKeywordSearch {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("documents")
    private List<Document> documents;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
