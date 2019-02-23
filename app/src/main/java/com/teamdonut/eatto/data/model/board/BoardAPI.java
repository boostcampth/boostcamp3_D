package com.teamdonut.eatto.data.model.board;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.Board;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface BoardAPI {
    @POST("board")
    Single<JsonObject> postBoard(
            @Body Board board,
            @Header("token") String token
    );

    @GET("board/today")
    Single<List<Board>> getTodayBoards(
    );

    @GET("home/board")
    Single<List<Board>> getRecommendBoards(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude
    );

    @GET("board/list/my")
    Single<List<Board>> getOwnBoard(
            @Header("kakao_id") long kakaoId
    );

    @GET("board/list/participation")
    Single<List<Board>> getParticipatedBoard(
            @Header("kakao_id") long kakaoId
    );

    @POST("board/participation")
    Single<JsonObject> postParticipateBoard(
            @Header("kakao_id") long kakaoId,
            @Body JsonObject jsonObject
    );

    @GET("map/list")
    Single<List<Board>> getAreaBoards(
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
            @Query("budget") String budget
    );

    // MyPage
    @GET("mypage/judge")
    Single<List<Board>> getJudgeBoards(
            @Header("kakao_id") long kakaoId
    );

    @PUT("mypage/judge")
    Single<JsonObject> putJudgeBoard(
            @Header("kakao_id") long kakaoId,
            @Header("token") String token,
            @Body JsonObject jsonObject
    );
}
