package com.teamdonut.eatto.model;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.kakao.LocalKeywordSearch;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BoardAPI {
    @POST("board")
    Single<Board> addBoard(@Body Board board);

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


    @GET("board/list/my")
    Single<List<Board>> getUserCreatedBoard(@Header("kakao_id") long kakaoId );

    @GET("board/list/participation")
    Single<List<Board>> getUserParticipatedBoard(@Header("kakao_id") long kakaoId);


    @GET("search/list")
    Single<List<Board>> getBoards(
            @Header("kakao_id") long kakaoId,
            @Query(value = "keyword", encoded = true) String keyword,
            @Query("min_time") int minTime,
            @Query("max_time") int maxTime,
            @Query("min_age") int minAge,
            @Query("max_age") int maxAge,
            @Query("max_person") int maxPerson,
            @Query("budget") int budget);

}
