package com.teamdonut.eatto.model;

import com.teamdonut.eatto.data.Board;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BoardAPI {
    @POST("board")
    Single<Board> addBoard(@Body Board board);
}
