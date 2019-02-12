package com.teamdonut.eatto.model;

import com.teamdonut.eatto.data.Board;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BoardAddAPI {

    @POST("board")
    Single<Board> addBoard(@Body Board board);

}
