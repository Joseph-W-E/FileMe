package com.example.joey.fileme.upload_image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.example.joey.fileme.R;
import com.example.joey.fileme.api.APIManagerSingleton;

/**
 * Created by Joey on 13-Nov-16.
 */

public class UploadImageActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        imageView = (ImageView) findViewById(R.id.upload_activity_iv_image);
    }

    /**
     * Launches camera methods to get an image displayed to the user.
     * @param v Temp.
     */
    public void takeImage(View v) {
        dispatchTakePictureIntent();
    }

    /**
     * Launches the retrofit task to upload the image.
     * @param v
     */
    public void uploadImage(View v) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        APIManagerSingleton manager = APIManagerSingleton.getInstance();
        
    }

    /*** Camera methods ***/

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

}
