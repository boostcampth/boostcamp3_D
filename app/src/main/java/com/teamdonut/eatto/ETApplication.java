package com.teamdonut.eatto;

import android.app.Application;
import com.bumptech.glide.Glide;
import com.facebook.stetho.Stetho;
import com.kakao.auth.KakaoSDK;
import com.teamdonut.eatto.util.kakao.KakaoSDKAdapter;

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
        Stetho.initializeWithDefaults(this);

        KakaoSDK.init(new KakaoSDKAdapter());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }


}
