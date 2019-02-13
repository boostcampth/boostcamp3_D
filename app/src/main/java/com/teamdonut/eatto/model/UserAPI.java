package com.teamdonut.eatto.model;

import com.teamdonut.eatto.data.User;
import io.reactivex.Observable;
import retrofit2.http.GET;

import java.util.ArrayList;

public interface UserAPI {

    @GET("home/ranking/user")
    Observable<ArrayList<User>> getTopTenUsers();
}