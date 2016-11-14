package com.example.joey.fileme.api;

import android.util.Base64;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.joey.fileme.request_image.server_image_view.ServerImageViewFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

    /**
     * Gets all the images stores on the server and stores them in the given container.
     * @param container The layout that will hold all retrieved images.
     */
    public void getImages(final LinearLayout container) {
        Call<JsonArray> call = api.getAllImages();

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    Log.i("GET SUCCESS", response.message());

                    for (JsonElement element : response.body()) {
                        Log.i("element", element.toString());
                        addJsonObjectToContainer(element.getAsJsonObject(), container);
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

    /**
     * Gets the image with the given name and stores it in the given container.
     * @param name The name of the image.
     * @param container The layout that will hold the retrieved image.
     */
    public void getImages(String name, final LinearLayout container) {
        Call<JsonObject> call = api.getImage(name);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.i("GET SUCCEESS", response.message());
                    addJsonObjectToContainer(response.body(), container);
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

    public void postImage(int id, String name, String desc, long date, byte[] data) {
        ServerData serverData = new ServerData(id, name, desc, date, Base64.encodeToString(data, Base64.DEFAULT));
        Call<JsonObject> call = api.postImage(serverData);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.i("POST SUCCESS", response.message());
                } else {
                    Log.i("POST FAILURE", response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("POST FAILURE", t.getLocalizedMessage());
            }
        });
    }

    /**
     * Converts the given JsonObject into usable data, and then adds the data to the container.
     * @param jsonObject The JsonObject to extract data from.
     * @param container The container to store the newly created view.
     */
    private void addJsonObjectToContainer(JsonObject jsonObject, LinearLayout container) {
        int id = -1;
        String name = "default";
        String desc = "default";
        long date = -1;
        byte[] data = null;

        if (!jsonObject.get("id").isJsonNull())
            id = jsonObject.get("id").getAsInt();

        if (!jsonObject.get("name").isJsonNull())
            name = jsonObject.get("name").getAsString();

        if (!jsonObject.get("description").isJsonNull())
            desc = jsonObject.get("description").getAsString();

        if (!jsonObject.get("date").isJsonNull())
            date = jsonObject.get("date").getAsLong();

        if (!jsonObject.get("data").isJsonNull())
            data = Base64.decode(jsonObject.get("data").getAsString(), Base64.DEFAULT);

        container.addView(new ServerImageViewFactory(container.getContext(), data)
                .addID(id)
                .addName(name)
                .addDescription(desc)
                .addDate(date)
                .build());
    }
    
}
