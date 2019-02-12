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

    @SerializedName("profile_image")
    private String profileImage;

    @SerializedName("age")
    private int age;


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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}