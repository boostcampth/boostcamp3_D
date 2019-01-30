package com.teamdonut.eatto;

import android.app.Application;
import com.bumptech.glide.Glide;
import com.facebook.stetho.Stetho;

public class ETApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Glide.with(this);
        Stetho.initializeWithDefaults(this);
    }
}
