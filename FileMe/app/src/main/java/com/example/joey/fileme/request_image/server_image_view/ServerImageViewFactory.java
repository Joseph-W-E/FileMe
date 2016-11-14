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
        parent.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setPadding(8, 8, 8, 8);

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

    public ServerImageViewFactory addName(String name) {
        parent.addView(buildTextView(name));
        return this;
    }

    public ServerImageViewFactory addDescription(String description) {
        parent.addView(buildTextView(description));
        return this;
    }

    public ServerImageViewFactory addID(int id) {
        parent.addView(buildTextView("" + id));
        return this;
    }

    public ServerImageViewFactory addDate(long date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        parent.addView(buildTextView(format.format(new Date(date))));
        return this;
    }

    public LinearLayout build() {
        return parent;
    }

    private TextView buildTextView(String text) {
        TextView textView = new TextView(parent.getContext());
        textView.setText(text);
        return textView;
    }

}
