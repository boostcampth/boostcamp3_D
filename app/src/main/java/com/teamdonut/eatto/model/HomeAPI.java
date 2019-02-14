package com.teamdonut.eatto.model;

import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.User;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.List;

public interface HomeAPI {

    @GET("home/ranking/user")
    Observable<List<User>> getTopTenUsers();

    @GET("home/home")
    Observable<List<Board>> getRecommendBoards();

    @GET("home/ranking/me")
    Single<List<User>> getRankUser(
            @Header("kakao_id") long kakaoId
    );
}