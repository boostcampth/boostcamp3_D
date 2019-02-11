package com.teamdonut.eatto.common.util;

import com.teamdonut.eatto.data.Keyword;

import java.util.Date;

import io.realm.Realm;

public class RealmDataHelper {

    public static void insertKeyword(Realm realm, final String content) {
        realm.executeTransactionAsync(r ->{
           Keyword keyword = r.createObject(Keyword.class);

           keyword.setContent(content);
           keyword.setSearchDate(new Date());
        });
    }

}
