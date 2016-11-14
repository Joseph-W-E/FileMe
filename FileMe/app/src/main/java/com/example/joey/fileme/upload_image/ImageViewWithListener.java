package com.example.joey.fileme.upload_image;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Joey on 13-Nov-16.
 */

public class ImageViewWithListener extends ImageView {

    public interface OnSetImageBitmapListener {
        void updateData();
    }

    private OnSetImageBitmapListener listener;

    public ImageViewWithListener(Context context) {
        super(context);
    }

    public ImageViewWithListener(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnSetImageBitmapListener(OnSetImageBitmapListener listener) {
        this.listener = listener;
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);

        if (listener != null)
            listener.updateData();
    }
}
