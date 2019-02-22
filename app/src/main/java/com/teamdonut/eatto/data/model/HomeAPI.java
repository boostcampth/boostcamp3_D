package com.teamdonut.eatto.data.model;

import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface HomeAPI {

    @GET("home/ranking/user")
    Single<List<User>> getTopTenUsers();

    @GET("home/board")
    Single<List<Board>> getRecommendBoards(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude
    );

    @GET("home/ranking/me")
    Single<User> getRankUser(
            @Header("kakao_id") long kakaoId
    );

    @GET("board/today")
    Single<List<Board>> getAllBoards(
    );
}