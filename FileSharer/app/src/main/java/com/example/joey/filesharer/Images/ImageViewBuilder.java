package com.example.joey.filesharer.Images;

/**
 * Created by Joey on 11-Nov-16.
 */

public class ImageViewBuilder {

    private String title, description;
    private int id;
    private byte[] data;

    public ImageViewBuilder(byte[] data) {
        this.data = data;
    }

    public ImageViewBuilder addTitle(String title) {
        this.title = title;
        return this;
    }

    public ImageViewBuilder addDescription(String description) {
        this.description = description;
        return this;
    }

    public ImageViewBuilder addID(int id) {
        this.id = id;
        return this;
    }

    public void build() {

    }
}
