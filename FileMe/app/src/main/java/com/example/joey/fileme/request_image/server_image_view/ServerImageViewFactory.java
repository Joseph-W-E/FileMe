package com.example.joey.fileme.request_image.server_image_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Joey on 13-Nov-16.
 */

public class ServerImageViewFactory {

    private LinearLayout parent;

    public ServerImageViewFactory(Context context, byte[] data) {
        // Immutable bitmap
        Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
        // Make it mutable
        image = image.copy(Bitmap.Config.ARGB_8888, true);

        ImageView imageView = new ImageView(parent.getContext());
        imageView.setImageBitmap(image);

        parent = new LinearLayout(context);

        parent.addView(imageView);
    }

    public void addTitle(String title) {
        TextView textView = new TextView(parent.getContext());
        textView.setText(title);
        parent.addView(textView);
    }

    public void addDescription(String description) {
        TextView textView = new TextView(parent.getContext());
        textView.setText(description);
        parent.addView(textView);
    }

    public void addID(int id) {
        TextView textView = new TextView(parent.getContext());
        textView.setText(id);
        parent.addView(textView);
    }

    public LinearLayout build() {
        return parent;
    }

}
