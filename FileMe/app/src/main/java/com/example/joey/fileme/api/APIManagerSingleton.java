package com.example.joey.fileme.api;

import android.util.Log;
import android.widget.LinearLayout;

import com.example.joey.fileme.request_image.server_image_view.ServerImageViewFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joey on 13-Nov-16.
 */

public class APIManagerSingleton {

    private static APIManagerSingleton instance = new APIManagerSingleton();

    private Retrofit retrofit;
    private APIInterface api;

    private APIManagerSingleton() {
        retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(APIInterface.class);
    }

    public static APIManagerSingleton getInstance() {
        return instance;
    }

    public void getImages(final LinearLayout container) {
        Call<JsonArray> call = api.getAllImages();

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    Log.i("GET SUCCESS", response.message());

                    for (JsonElement element : response.body()) {
                        Log.i("element", element.toString());
                        JsonObject jsonObject = element.getAsJsonObject();

                        int id = jsonObject.get("id").getAsInt();
                        String name = jsonObject.get("name").getAsString();
                        long date = jsonObject.get("date").getAsLong();
                        byte[] data = null;
                        String desc = "";
                        if (!jsonObject.get("description").isJsonNull())
                            desc = jsonObject.get("description").getAsString();

                        container.addView(new ServerImageViewFactory(container.getContext(), data)
                                .addID(id)
                                .addTitle(name)
                                .addDescription(desc)
                                .addDate(date)
                                .build());

                    }
                } else {
                    Log.i("GET FAILURE", response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.i("GET FAILURE", t.getLocalizedMessage());
            }
        });
    }

    public void getImages(String title, final LinearLayout container) {
        Call<JsonObject> call = api.getImage(title);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.i("GET SUCCEESS", response.message());
                    JsonObject jsonObject = response.body();
                    
                    int id = jsonObject.get("id").getAsInt();
                    String name = jsonObject.get("name").getAsString();
                    long date = jsonObject.get("date").getAsLong();
                    byte[] data = null;
                    String desc = "";
                    if (!jsonObject.get("description").isJsonNull())
                        desc = jsonObject.get("description").getAsString();

                    container.addView(new ServerImageViewFactory(container.getContext(), data)
                            .addID(id)
                            .addTitle(name)
                            .addDescription(desc)
                            .addDate(date)
                            .build());

                } else {
                    Log.i("GET FAILURE", response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("GET FAILURE", t.getLocalizedMessage());
            }
        });
    }

    public void postImage() {
        // TODO
    }
    
}
