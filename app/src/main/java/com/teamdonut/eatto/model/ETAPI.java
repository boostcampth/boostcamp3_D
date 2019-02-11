package com.teamdonut.eatto.model;

import com.teamdonut.eatto.data.Board;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ETAPI {

    @GET("board")
    Observable<Board> getBoards(
            @Header("et-auth-token") String token);

}
