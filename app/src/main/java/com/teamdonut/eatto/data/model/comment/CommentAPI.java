package com.teamdonut.eatto.data.model.comment;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.Comment;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommentAPI {
    @GET("board/comment")
    Single<List<Comment>> getComments(
            @Header("kakao_id") long kakaoId,
            @Query("board_id") int boardId);

    @POST("board/comment")
    Single<JsonObject> postComments(
            @Header("kakao_id") long kakaoId,
            @Header("token") String token,
            @Body Comment comment);
}
