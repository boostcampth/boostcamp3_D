package com.teamdonut.eatto.data.model.user;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.User;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.List;

public interface UserAPI {

    @POST("user")
    Single<JsonObject> postUser(
            @Body User user
    );

    @GET("user/info")
    Single<User> getUser(
            @Header("kakao_id") long kakaoId
    );


    @GET("home/ranking/user")
    Single<List<User>> getTopTenUsers();
}
