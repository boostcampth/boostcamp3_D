package com.teamdonut.eatto.data.model.kakao;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.kakao.LocalKeywordSearch;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoAPI {
    @GET("v2/local/geo/coord2regioncode.json")
    Single<JsonObject> getMyAddress(
            @Header("Authorization") String token,
            @Query("x") String x,
            @Query("y") String y);

    @GET("v2/local/search/keyword.json")
    Single<LocalKeywordSearch> getAddress(
            @Header("Authorization") String authorization,
            @Query("query") String query,
            @Query("page") int page,
            @Query("size") int size);
}
