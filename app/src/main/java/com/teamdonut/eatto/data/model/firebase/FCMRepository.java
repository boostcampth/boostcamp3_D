package com.teamdonut.eatto.data.model.firebase;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.model.ServiceGenerator;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FCMRepository {
    private FCMAPI service = ServiceGenerator.createService(FCMAPI.class, ServiceGenerator.BASE);

    public static FCMRepository getInstance() {
        return LazyInit.INSTANCE;
    }

    private static class LazyInit {
        private static final FCMRepository INSTANCE = new FCMRepository();
    }

    public Disposable postFCMToken(Consumer<JsonObject> subscribeConsumer, String token) {
        return service.postFCMToken(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(subscribeConsumer);
    }

}
