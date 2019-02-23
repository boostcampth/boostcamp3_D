package com.teamdonut.eatto.data.model.firebase;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FCMAPI {

    @POST("fcm/token")
    Single<JsonObject> postFCMToken(@Header("token") String token);

}
