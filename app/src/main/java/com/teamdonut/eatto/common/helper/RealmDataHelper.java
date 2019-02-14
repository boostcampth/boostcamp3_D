package com.teamdonut.eatto.common.helper;

import com.teamdonut.eatto.data.Keyword;

import java.util.Date;

import com.teamdonut.eatto.data.User;

import io.realm.Realm;

public class RealmDataHelper {

    /**
     * get User kakao Id (access)
     */
    public static long getAccessId() {
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        User copyUser;

        if (user != null) {
            copyUser = realm.copyFromRealm(user);
            realm.close();

            return copyUser.getKakaoId();
        } else {
            realm.close();
            throw new NullPointerException("There is no user data.");
        }
    }

    /**
     * Insert keyword
     *
     * @param realm
     * @param content keyword
     */
    public static void insertKeyword(Realm realm, final String content) {
        realm.executeTransactionAsync(r -> {
            Keyword keyword = r.createObject(Keyword.class);

            keyword.setContent(content);
            keyword.setSearchDate(new Date());
        });
    }

    /**
     * Insert user.
     *
     * @param realm
     * @param kakaoId  user's kakao id
     * @param nickName user's nickName
     * @param photo    user's profile image url
     */
    public static void insertUser(Realm realm, final long kakaoId, final String nickName, final String photo) {
        realm.executeTransactionAsync(r -> {
            User user = r.createObject(User.class, kakaoId);

            user.setNickName(nickName);
            user.setPhoto(photo);
        });
    }

    /**
     * update user.
     *
     * @param nickName user's nickName
     * @param sex      user's sex
     * @param photo    user's profile image url
     */
    public static void updateUser(Realm realm, final String nickName, final int sex, final String photo) {
        realm.executeTransactionAsync(r -> {
            final User user = r.where(User.class).findFirst();

            user.setNickName(nickName);
            user.setSex(sex);
            user.setPhoto(photo);
        });
    }

    /**
     * update user.
     *
     * @param nickName user's nickName
     * @param sex      user's sex
     * @param photo    user's profile image url
     */
    public static void updateUser(Realm realm, final String nickName, final int sex, final String photo, final int age) {
        realm.executeTransactionAsync(r -> {
            final User user = r.where(User.class).findFirst();

            user.setNickName(nickName);
            user.setSex(sex);
            user.setPhoto(photo);
            user.setAge(age);
        });
    }
}