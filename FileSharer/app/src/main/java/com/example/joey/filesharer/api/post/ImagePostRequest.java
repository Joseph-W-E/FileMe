package com.example.joey.filesharer.api.post;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Joey on 11-Nov-16.
 */

public class ImagePostRequest {

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("date")
    public long date;

    @SerializedName("data")
    public byte[] data;

    public ImagePostRequest(String title, String description, long date, byte[] data) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.data = data;
    }
}
