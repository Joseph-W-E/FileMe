package com.example.joey.fileme.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Joey on 13-Nov-16.
 */

public interface APIInterface {


    String URL = "http://65.110.227.87:4567";


    /*** GET REQUESTS ***/

    @GET("/get/{name}")
    Call<JsonObject> getImage(@Path("name") String name);


    /*** POST REQUESTS ***/

    @Headers({"Content-Type: application/json"})
    @POST("/post")
    Call<JsonObject> postImage(@Body ServerData body);


}
