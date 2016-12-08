package com.example.joey.fileme.request_image.server_image_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joey.fileme.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Joey on 13-Nov-16.
 */

public class ServerImageViewBuilder {

    private LinearLayout main;
    private ImageView imageView;
    private TextView txtID, txtName, txtDesc, txtDate;

    public ServerImageViewBuilder(Context context, byte[] data) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        main      = (LinearLayout) inflater.inflate(R.layout.server_image_view_item, null);
        imageView = (ImageView) main.findViewById(R.id.server_image_view_item_image_view);
        txtID     = (TextView) main.findViewById(R.id.server_image_view_item_txt_id);
        txtName   = (TextView) main.findViewById(R.id.server_image_view_item_txt_name);
        txtDesc   = (TextView) main.findViewById(R.id.server_image_view_item_txt_description);
        txtDate   = (TextView) main.findViewById(R.id.server_image_view_item_txt_date);

        /*** Not all views are going to be visible ***/
        txtID  .setVisibility(View.GONE);
        txtName.setVisibility(View.GONE);
        txtDesc.setVisibility(View.GONE);
        txtDate.setVisibility(View.GONE);

        if (data != null) {
            Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length); // immutable
            image = image.copy(Bitmap.Config.ARGB_8888, true);                  // mutable
            imageView.setImageBitmap(image);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Make the image blow up to take up the entire screen
                }
            });
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    public ServerImageViewBuilder addName(String name) {
        txtName.setText(String.format(Locale.ENGLISH, "Name: %s", name));
        txtName.setVisibility(View.VISIBLE);
        return this;
    }

    public ServerImageViewBuilder addDescription(String description) {
        txtDesc.setText(String.format(Locale.ENGLISH, "Desc: %s", description));
        txtDesc.setVisibility(View.VISIBLE);
        return this;
    }

    public ServerImageViewBuilder addID(int id) {
        txtID.setText(String.format(Locale.ENGLISH, "ID: %d", id));
        txtID.setVisibility(View.VISIBLE);
        return this;
    }

    public ServerImageViewBuilder addDate(long date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
        txtDate.setText(String.format(Locale.ENGLISH, "Date: %s", format.format(date)));
        txtDate.setVisibility(View.VISIBLE);
        return this;
    }

    public LinearLayout build() {
        return main;
    }

}
