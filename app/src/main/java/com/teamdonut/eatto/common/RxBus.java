package com.teamdonut.eatto.common;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


public class RxBus {
    private static RxBus mInstance;
    private BehaviorSubject<Object> mSubject;

    private RxBus() {
        mSubject = BehaviorSubject.create();
    }

    public static RxBus getInstance() {
        return LazyInit.INSTANCE;
    }

    private static class LazyInit {
        private static final RxBus INSTANCE = new RxBus();
    }

    public static void setInstanceToNull() {
        mInstance = null;
    }

    public void sendBus(Object object) {
        mSubject.onNext(object);
    }

    public Observable<Object> getBus() {
        return mSubject;
    }


}
