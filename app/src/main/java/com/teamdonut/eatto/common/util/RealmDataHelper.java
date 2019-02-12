package com.teamdonut.eatto.common.util;

import com.teamdonut.eatto.data.Keyword;

import java.util.Date;

import com.teamdonut.eatto.data.User;
import io.realm.Realm;

public class RealmDataHelper {

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
     * @param realm
     * @param kakaoId user's kakao id
     * @param nickName user's nickName
     * @param profileImage user's profile image url
     */
    public static void insertUser(Realm realm, final long kakaoId, final String nickName, final String profileImage) {
        realm.executeTransactionAsync(r -> {
            User user = r.createObject(User.class, kakaoId);

            user.setNickName(nickName);
            user.setProfileImage(profileImage);
        });
    }

    /**
     * update user.
     * @param nickName user's nickName
     * @param sex user's sex
     * @param profileImage user's profile image url
     */
    public static void updateUser(Realm realm, final String nickName, final int sex, final String profileImage) {
        realm.executeTransactionAsync(r -> {
            final User user = r.where(User.class).findFirst();

            user.setNickName(nickName);
            user.setSex(sex);
            user.setProfileImage(profileImage);
        });
    }
}
