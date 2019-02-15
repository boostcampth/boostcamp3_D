package com.teamdonut.eatto;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.facebook.stetho.Stetho;
import com.kakao.auth.KakaoSDK;
import com.teamdonut.eatto.common.util.kakao.KakaoSDKAdapter;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;

public class ETApplication extends Application {

    private static volatile ETApplication instance = null;

    public static ETApplication getETApplicationContext() {
        if (instance == null) {
            throw new IllegalStateException("this application does not inherit ETApplication.");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Glide.with(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        KakaoSDK.init(new KakaoSDKAdapter());

        Realm.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }


}
