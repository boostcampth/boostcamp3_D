package com.teamdonut.eatto.common.helper;

import com.teamdonut.eatto.data.Keyword;
import com.teamdonut.eatto.data.User;

import java.util.Date;

import io.realm.Realm;

public class RealmDataHelper {

    public static User getUser() {
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        User copyUser;

        if (user != null) {
            copyUser = realm.copyFromRealm(user);
            realm.close();

            return copyUser;
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
     * update user
     * @param realm
     * @param u user
     */
    public static void updateUser(Realm realm, final User u) {
        realm.executeTransactionAsync(r -> {
            final User user = r.where(User.class).findFirst();

            user.setNickName(u.getNickName());
            user.setSex(u.getSex());
            user.setPhoto(u.getPhoto());
            user.setAge(u.getAge());
        });
    }
}
