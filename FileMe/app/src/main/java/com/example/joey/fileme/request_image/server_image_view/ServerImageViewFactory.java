package com.example.joey.fileme.request_image.server_image_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Joey on 13-Nov-16.
 */

public class ServerImageViewFactory {

    private LinearLayout parent;

    public ServerImageViewFactory(Context context, byte[] data) {
        parent = new LinearLayout(context);

        if (data != null) {
            // Immutable bitmap
            Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
            // Make it mutable
            image = image.copy(Bitmap.Config.ARGB_8888, true);

            ImageView imageView = new ImageView(parent.getContext());
            imageView.setImageBitmap(image);

            parent.addView(imageView);
        }
    }

    public ServerImageViewFactory addTitle(String title) {
        TextView textView = new TextView(parent.getContext());
        textView.setText(title);
        parent.addView(textView);
        return this;
    }

    public ServerImageViewFactory addDescription(String description) {
        TextView textView = new TextView(parent.getContext());
        textView.setText(description);
        parent.addView(textView);
        return this;
    }

    public ServerImageViewFactory addID(int id) {
        TextView textView = new TextView(parent.getContext());
        textView.setText("" + id);
        parent.addView(textView);
        return this;
    }

    public ServerImageViewFactory addDate(long date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        TextView textView = new TextView(parent.getContext());
        textView.setText(format.format(new Date(date)));
        parent.addView(textView);
        return this;
    }

    public LinearLayout build() {
        return parent;
    }

}
