package com.teamdonut.eatto.data.model.board;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.BoardAddInformation;
import com.teamdonut.eatto.data.kakao.LocalKeywordSearch;
import io.reactivex.Single;
import retrofit2.http.*;

import java.util.List;

public interface BoardAPI {
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
            @Query("budget") String budget);

    @POST("board")
    Single<Board> addBoard(
            @Body Board board);

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
    Single<List<Board>> getUserCreatedBoard(
            @Header("kakao_id") long kakaoId);

    @GET("board/list/participation")
    Single<List<Board>> getUserParticipatedBoard(
            @Header("kakao_id") long kakaoId);

    /**
     * 사용자 게시글 참여 요청.
     *
     * @param kakaoId             유저 kakao id
     * @param boardAddInformation BoardAddInformation 클래스
     * @return Single
     */
    @POST("board/participation")
    Single<JsonObject> joinBoard(
            @Header("kakao_id") long kakaoId,
            @Body BoardAddInformation boardAddInformation);
}
