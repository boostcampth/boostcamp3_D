package com.teamdonut.eatto.common;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


public class RxBus {
    private static BehaviorSubject<Object> mSubject;

    private RxBus() {
        mSubject = BehaviorSubject.create();
    }

    public static RxBus getInstance() {
        if(mSubject !=null && mSubject.hasComplete()) {
            mSubject = BehaviorSubject.create();
        }
        return LazyInit.INSTANCE;
    }

    public static void resetBus() {
        mSubject.onComplete();
    }

    private static class LazyInit {
        private static final RxBus INSTANCE = new RxBus();
    }

    public void sendBus(Object object) {
        mSubject.onNext(object);
    }

    public Observable<Object> getBus() {
        return mSubject;
    }


}
