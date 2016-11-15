package com.example.joey.fileme.upload_image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.joey.fileme.R;
import com.example.joey.fileme.api.APIManagerSingleton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Joey on 13-Nov-16.
 */

public class UploadImageActivity extends Activity {

    private static final int REQUEST_TAKE_PHOTO  = 1;

    private Context context;

    private Uri imageUri;
    private String encodedData;
    private ImageViewWithListener imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        context = this;

        imageView = (ImageViewWithListener) findViewById(R.id.upload_activity_iv_image);
        imageView.setOnSetImageBitmapListener(new ImageViewWithListener.OnSetImageBitmapListener() {
            @Override
            public void updateData() {
                // This guarantees that the string encodedData will always match the imageView
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream .toByteArray();
                encodedData = Base64.encodeToString(bytes, Base64.DEFAULT);
            }
        });
    }

    /*** Upload Image Methods ***/

    public void uploadImage(View v) {
        ImageInformationDialog dialog = new ImageInformationDialog();
        dialog.show(getFragmentManager(), "Please Enter Image Information");
    }

    public void kickOffUpload(final String name, final String desc) {
        Toast.makeText(this, "Uploading image to server!", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                APIManagerSingleton manager = APIManagerSingleton.getInstance();
                manager.postImage(name, desc, Calendar.getInstance().getTimeInMillis(), encodedData, context);
            }
        }).run();
    }

    /*** Take Image Methods ***/

    public void takeImage(View v) {
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                imageUri = photoURI;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO  && resultCode == RESULT_OK) {
            Toast.makeText(context, "Loading bitmap...", Toast.LENGTH_LONG).show();
            BitmapAsyncTask task = new BitmapAsyncTask();
            task.execute();
        }
    }

    private File createImageFile() throws IOException {
        File image = File.createTempFile("temp", ".png", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        return image;
    }

    class BitmapAsyncTask extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to load bitmap.", Toast.LENGTH_LONG).show();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

}
