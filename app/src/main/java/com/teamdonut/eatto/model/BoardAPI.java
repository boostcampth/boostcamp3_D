package com.teamdonut.eatto.model;

import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.kakao.LocalKeywordSearch;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BoardAPI {
    @POST("board")
    Single<Board> addBoard(@Body Board board);

    @GET("v2/local/search/keyword.json")
    Single<LocalKeywordSearch> getAddress(
            @Header("Authorization") String authorization,
            @Query("query") String query,
            @Query("page") int page,
            @Query("size") int size);

}
