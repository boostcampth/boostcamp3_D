package com.teamdonut.eatto.model;

import com.teamdonut.eatto.data.User;
import io.reactivex.Observable;
import retrofit2.http.GET;

import java.util.List;

public interface HomeAPI {

    @GET("home/ranking/user")
    Observable<List<User>> getTopTenUsers();

}