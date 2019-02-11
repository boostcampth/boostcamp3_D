package com.teamdonut.eatto.model;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import com.facebook.stetho.okhttp3.StethoInterceptor;

public final class ServiceGenerator {

    private final static String BASE_URL = "http://54.180.113.135/";

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(true)
                .build();
    }

    private static Retrofit getInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static <T> T createService(Class<T> serviceClass) {
        return getInstance().create(serviceClass);
    }
}
