package com.example.joey.fileme.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Joey on 13-Nov-16.
 */

public class ServerData {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("date")
    private long date;

    @SerializedName("data")
    private String data;

    public ServerData(String name, String description, long date, String data) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
