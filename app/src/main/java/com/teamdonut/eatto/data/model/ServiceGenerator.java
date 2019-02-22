package com.teamdonut.eatto.data.model;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ServiceGenerator {
    private final static String BASE_URL = "http://54.180.113.135/";
    private final static String KAKAO_URL = "https://dapi.kakao.com/";
    public final static int BASE = 1;
    public final static int KAKAO = 2;

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(true)
                .build();
    }

    private static Retrofit getInstance() {
        return LazyInit.INSTANCE;
    }

    private static Retrofit getKakaoInstance() {
        return LazyInit.KAKAO_INSTANCE;
    }

    private static class LazyInit {
        private static final Retrofit INSTANCE = createInstance(BASE_URL);
        private static final Retrofit KAKAO_INSTANCE = createInstance(KAKAO_URL);

        private static Gson createGson() {
            return new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
        }

        private static Retrofit createInstance(String url) {
            return new Retrofit.Builder()
                    .baseUrl(url)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(createGson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    public static <T> T createService(Class<T> serviceClass, int flag) {
        switch (flag) {
            case BASE:
                return getInstance().create(serviceClass);
            case KAKAO:
                return getKakaoInstance().create(serviceClass);
            default:
                return null;
        }
    }
}
