package com.teamdonut.eatto.common;

import androidx.lifecycle.MutableLiveData;

public class LiveBus {
    private MutableLiveData<Object> mSubject;
    private LiveBus() {
        mSubject = new MutableLiveData<>();
    }

    public static LiveBus getInstance() {
        return LazyInit.INSTANCE;
    }

    private static class LazyInit {
        private static final LiveBus INSTANCE = new LiveBus();
    }

    public void sendBus(Object object) {
        mSubject.postValue(object);
    }

    public MutableLiveData<Object> getBus() {
        return mSubject;
    }

}
