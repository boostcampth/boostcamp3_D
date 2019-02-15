package com.teamdonut.eatto.model;

import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.User;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserAPI {

    @POST("user")
    Single<JsonObject> postUserInfo(
            @Body User user);

    @GET("user/info")
    Single<User> getUserInfo(
            @Header("kakao_id") long kakaoId);
}
