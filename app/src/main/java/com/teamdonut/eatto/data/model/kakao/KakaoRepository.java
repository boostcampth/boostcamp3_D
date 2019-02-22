package com.teamdonut.eatto.data.model.kakao;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.kakao.LocalKeywordSearch;
import com.teamdonut.eatto.data.model.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class KakaoRepository {
    private KakaoAPI service = ServiceGenerator.createService(KakaoAPI.class, ServiceGenerator.KAKAO);

    public static KakaoRepository getInstance() {
        return LazyInit.INSTANCE;
    }

    private static class LazyInit {
        private static final KakaoRepository INSTANCE = new KakaoRepository();
    }

    public Disposable getMyAddress(Consumer<JsonObject> subscribeConsumer, String kakaoKey, String longtitude, String latitude) {
        return service.getMyAddress(kakaoKey, longtitude, latitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(subscribeConsumer);
    }

    public Disposable getAddress(Consumer<LocalKeywordSearch> subscribeConsumer, String authorization, String query, int page, int size) {
        return service.getAddress(authorization, query, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribeConsumer);
    }
}
