package com.example.joey.fileme.upload_image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.joey.fileme.R;
import com.example.joey.fileme.api.APIManagerSingleton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Calendar;

/**
 * Created by Joey on 13-Nov-16.
 */

public class UploadImageActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageViewWithListener imageView;

    private String encodedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        imageView = (ImageViewWithListener) findViewById(R.id.upload_activity_iv_image);
        imageView.setOnSetImageBitmapListener(new ImageViewWithListener.OnSetImageBitmapListener() {
            @Override
            public void updateData() {
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
                bitmap.copyPixelsToBuffer(buffer);
                encodedData = Base64.encodeToString(buffer.array(), Base64.DEFAULT);
            }
        });
    }

    /*** Upload Image Methods ***/

    public void uploadImage(View v) {
        ImageInformationDialog dialog = new ImageInformationDialog();
        dialog.show(getFragmentManager(), "Please Enter Image Information");
    }

    public void kickOffUpload(String name, String desc) {
        APIManagerSingleton manager = APIManagerSingleton.getInstance();
        manager.postImage(name, desc, Calendar.getInstance().getTimeInMillis(), encodedData);
    }

    /*** Take Image Methods ***/

    public void takeImage(View v) {
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }


    public void test(View v) {
        byte[] arr = Base64.decode(encodedData, Base64.DEFAULT);
        Bitmap temp = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        if (temp == null)
            Log.i("test", "null bitmap");
        imageView.setImageBitmap(temp);
    }

}
