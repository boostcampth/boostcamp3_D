package com.teamdonut.eatto.model;

import com.teamdonut.eatto.data.Board;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface MyPageAPI {
    @GET("mypage/judge")
    Single<List<Board>> getJudgeBoards(
            @Header("kakao_id") long kakaoId
    );
}
