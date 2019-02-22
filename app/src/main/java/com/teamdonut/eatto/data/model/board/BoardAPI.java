package com.teamdonut.eatto.data.model.board;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.Board;
import io.reactivex.Single;
import retrofit2.http.*;

import java.util.List;

public interface BoardAPI {
    @POST("board")
    Single<JsonObject> postBoard(
            @Body Board board
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
    Single<List<Board>> getUserCreatedBoard(
            @Header("kakao_id") long kakaoId
    );

    @GET("board/list/participation")
    Single<List<Board>> getUserParticipatedBoard(
            @Header("kakao_id") long kakaoId
    );

    @POST("board/participation")
    Single<JsonObject> joinBoard(
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
            @Body JsonObject jsonObject
    );
}
