package com.example.joey.fileme.api;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

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
    public void getImages(final View container) {
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
    public void getImages(String name, final View container) {
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

    /**
     * Sends the image and it's respective data to the server to store it.
     * @param name The image name.
     * @param desc The image description.
     * @param date The image date (in milliseconds).
     * @param data The image data encoded as base64.
     */
    public void postImage(String name, String desc, long date, String data, final Context context) {
        ServerData serverData = new ServerData(name, desc, date, data);
        Call<JsonObject> call = api.postImage(serverData);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.i("POST SUCCESS", response.message());
                    Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
                } else {
                    Log.i("POST FAILURE", response.message());
                    Toast.makeText(context, "Failed to upload...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("POST FAILURE", t.getLocalizedMessage());
                Toast.makeText(context, "Failed to upload...", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Converts the given JsonObject into usable data, and then adds the data to the container.
     * @param jsonObject The JsonObject to extract data from.
     * @param container The container to store the newly created view.
     */
    private void addJsonObjectToContainer(JsonObject jsonObject, View container) {
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

        ServerImageViewFactory factory = new ServerImageViewFactory(container.getContext(), data)
                .addID(id)
                .addName(name);
        if (!desc.equals("default"))
            factory.addDescription(desc);
        factory.addDate(date);

        ((ScrollView) container).addView(factory.build());
        
    }
    
}
