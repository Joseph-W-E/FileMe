package com.example.joey.filesharer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.joey.filesharer.api.APIInterface;
import com.example.joey.filesharer.api.get.ImageNameDialog;
import com.example.joey.filesharer.api.post.ImageDescriptorDialog;
import com.example.joey.filesharer.api.post.ImagePostRequest;
import com.google.gson.JsonObject;

import java.nio.ByteBuffer;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*** View File methods ***/

    public void dispatchViewImageService(View v) {
        // Prompt the user for a name
        ImageNameDialog dialog = new ImageNameDialog();
        dialog.show(getFragmentManager(), "Search For Image");
    }

    public void getImage(String title) {
        JSONCallToGetImage(title);
    }

    private void JSONCallToGetImage(String title) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface api = retrofit.create(APIInterface.class);

        Call<JsonObject> call = api.getImage(title);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.i("GET SUCCEEDED", response.message());

                    JsonObject jdata = response.body();

                    //id name description date data

                    int id = jdata.get("id").getAsInt();
                    String name = jdata.get("name").getAsString();
                    String description = jdata.get("description").getAsString();
                    long date = jdata.get("date").getAsLong();

                    Log.i("info", String.format(
                            "id: %d, name: %s, description: %s, date: %d",
                            id, name, description, date
                    ));

                } else {
                    Log.w("GET FAILED", response.message());

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w("GET FAILED", "The get request failed - " + t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /*** Take and Upload File methods ***/

    /**
     * Step 1 : Get the image from the camera.
     * @param v The button.
     */
    public void dispatchTakePictureIntent(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    /**
     * Step 2 : if the user successfully got an image, prompt the user with a dialog to give
     * the image information.
     * @param requestCode The request code.
     * @param resultCode The result code.
     * @param data The data being passed back.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Get the bitmap
            Bundle extras = data.getExtras();
            image = (Bitmap) extras.get("data");

            if (image == null) return;

            // Launch the dialog so the user can add a title, description, and date
            ImageDescriptorDialog dialog = new ImageDescriptorDialog();
            dialog.show(getFragmentManager(), "Add Information");
        }
    }

    /**
     * Step 3 : After the users has given the image information, convert the image to a byte array.
     * @param image The image to convert to a byte array.
     * @return A byte array representation of the image taken by the user.
     */
    private byte[] convertToByteArray(Bitmap image) {
        int bytes = image.getByteCount();
        ByteBuffer buffer = ByteBuffer.allocate(bytes);
        image.copyPixelsToBuffer(buffer);
        return buffer.array();
    }

    /**
     * Step 4 : Upload the image to the web server by launching the retrofit request.
     * @param title
     * @param description
     */
    public void uploadImage(String title, String description) {
        Calendar date = Calendar.getInstance();
        byte[] data = convertToByteArray(image);
        image = null;

        // Now that we have all the data, send it to the server
        ImagePostRequest request = new ImagePostRequest(title, description, date.getTimeInMillis(), data);
        JSONCallToPostImage(request);
    }

    /**
     * Step 5 : Finish uploading the image.
     * @param imagePostRequest
     */
    private void JSONCallToPostImage(ImagePostRequest imagePostRequest) {
        // Make the API call
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface api = retrofit.create(APIInterface.class);

        Call<JsonObject> call = api.postImage(imagePostRequest);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("lol", response.body().getAsString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("uh", t.getLocalizedMessage());
            }
        });
    }

}
