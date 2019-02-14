package com.teamdonut.eatto.model;

import com.google.gson.JsonObject;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface BoardSearchAPI {
    @GET("v2/local/geo/coord2regioncode.json")
    Observable<JsonObject> getMyAddress(
            @Header("Authorization") String token,
            @Query("x") String x,
            @Query("y") String y);
}
