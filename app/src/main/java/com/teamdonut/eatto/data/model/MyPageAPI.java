package com.teamdonut.eatto.data.model;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.Board;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

import java.util.List;

public interface MyPageAPI {
    @GET("mypage/judge")
    Single<List<Board>> getJudgeBoards(
            @Header("kakao_id") long kakaoId
    );

    @PUT("mypage/judge")
    Single<JsonObject> judgeBoard(
            @Header("kakao_id") long kakaoId,
            @Body JsonObject jsonObject
    );
}
