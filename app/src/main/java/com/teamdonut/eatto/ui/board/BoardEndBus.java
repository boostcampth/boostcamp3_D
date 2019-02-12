package com.teamdonut.eatto.ui.board;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


public class BoardEndBus {
    private static BoardEndBus mInstance;
    private BehaviorSubject<Object> mSubject;

    private BoardEndBus() {
        mSubject = BehaviorSubject.create();
    }

    public static BoardEndBus getInstance() {
        if (mInstance == null) {
            mInstance = new BoardEndBus();
        }
        return mInstance;
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
