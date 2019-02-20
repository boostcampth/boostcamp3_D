package com.teamdonut.eatto.model;

import com.teamdonut.eatto.data.Board;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

import java.util.List;

public interface MapAPI {
    @GET("map/list")
    Single<List<Board>> getBoards(
            @Query("left_longitude") double leftLongitude,
            @Query("left_latitude") double leftLatitude,
            @Query("right_longitude") double rightLongitude,
            @Query("right_latitude") double rightLatitude
    );

    @GET("search/list")
    Single<List<Board>> getSearchBoards(
            @Header("kakao_id") long kakaoId,
            @Query(value = "keyword", encoded = true) String keyword,
            @Query("min_time") int minTime,
            @Query("max_time") int maxTime,
            @Query("min_age") int minAge,
            @Query("max_age") int maxAge,
            @Query("max_person") int maxPerson,
            @Query("budget") String budget);
}
