package com.teamdonut.eatto.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class User implements RealmModel {

    @PrimaryKey
    @SerializedName("kakao_id")
    private long kakaoId;

    @SerializedName("nick_name")
    private String nickName;

    @SerializedName("sex")
    private int sex; //0 is female 1 is male

    @SerializedName("photo")
    private String photo;

    @SerializedName("age")
    private int age;

    @SerializedName("score_normal")
    private int scoreNormal;

    @SerializedName("score_good")
    private int scoreGood;

    @SerializedName("score_great")
    private int scoreGreat;

    @SerializedName("score_sum")
    private int scoreSum;

    @SerializedName("rank")
    private int rank;

    public long getKakaoId() {
        return kakaoId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScoreNormal() {
        return scoreNormal;
    }

    public int getScoreGood() {
        return scoreGood;
    }

    public int getScoreGreat() {
        return scoreGreat;
    }

    public int getScoreSum() {
        return scoreSum;
    }

    public int getRank() {
        return rank;
    }
}