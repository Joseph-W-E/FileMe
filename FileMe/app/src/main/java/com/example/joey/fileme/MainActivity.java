package com.example.joey.fileme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.joey.fileme.request_image.RequestImageActivity;
import com.example.joey.fileme.upload_image.UploadImageActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView i = (ImageView) findViewById(R.id.background);
        i.setImageBitmap(((BitmapDrawable)getDrawable(R.drawable.filecabinet)).getBitmap());
    }

    public void launchRequestImageActivity(View v) {
        startActivity(new Intent(this, RequestImageActivity.class));
    }

    public void launchUploadImageActivity(View v) {
        startActivity(new Intent(this, UploadImageActivity.class));
    }
}
