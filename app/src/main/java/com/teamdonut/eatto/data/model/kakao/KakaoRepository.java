package com.teamdonut.eatto.data.model.kakao;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.kakao.LocalKeywordSearch;
import com.teamdonut.eatto.data.model.ServiceGenerator;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KakaoRepository {
    private KakaoAPI service = ServiceGenerator.createService(KakaoAPI.class, ServiceGenerator.KAKAO);

    public static KakaoRepository getInstance() {
        return LazyInit.INSTANCE;
    }

    private static class LazyInit {
        private static final KakaoRepository INSTANCE = new KakaoRepository();
    }

    public Single<JsonObject> getMyAddress(String kakaoKey, String longtitude, String latitude) {
        return service.getMyAddress(kakaoKey, longtitude, latitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<LocalKeywordSearch> getAddress(String authorization, String query, int page, int size) {
        return service.getAddress(authorization, query, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
