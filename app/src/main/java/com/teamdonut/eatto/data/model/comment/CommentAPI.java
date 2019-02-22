package com.teamdonut.eatto.data.model.comment;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.Comment;
import io.reactivex.Single;
import retrofit2.http.*;

import java.util.List;

public interface CommentAPI {
    @GET("board/comment")
    Single<List<Comment>> getComments(
            @Header("kakao_id") long kakaoId,
            @Query("board_id") int boardId);

    @POST("board/comment")
    Single<JsonObject> postComments(
            @Header("kakao_id") long kakaoId,
            @Body Comment comment);
}
