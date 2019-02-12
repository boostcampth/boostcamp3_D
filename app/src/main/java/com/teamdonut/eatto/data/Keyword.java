package com.teamdonut.eatto.data;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Keyword implements RealmModel {

    private String content;
    private Date searchDate;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }
}
