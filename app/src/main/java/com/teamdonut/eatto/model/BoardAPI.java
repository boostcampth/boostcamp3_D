package com.teamdonut.eatto.model;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.BoardAddInformation;
import com.teamdonut.eatto.data.Comment;
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

    @GET("board/comment")
    Single<List<Comment>> getComments(
            @Header("kakao_id") long kakaoId,
            @Query("board_id") int boardId);

    @POST("board/comment")
    Single<JsonObject> postComments(
            @Header("kakao_id") long kakaoId,
            @Body Comment comment);
}
