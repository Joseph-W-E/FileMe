package com.example.joey.fileme.request_image;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.joey.fileme.R;
import com.example.joey.fileme.api.APIManagerSingleton;

/**
 * Created by Joey on 13-Nov-16.
 */

public class RequestImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_image);
    }

    public void requestImage(View v) {
        final EditText etTitle = (EditText) findViewById(R.id.request_image_activity_et_title);
        final LinearLayout container = (LinearLayout) findViewById(R.id.request_image_activity_image_container);
        APIManagerSingleton manager = APIManagerSingleton.getInstance();

        String text = etTitle.getText().toString();
        if (text.isEmpty()) {
            manager.getImages(container);
        } else {
            manager.getImages(text, container);
        }
    }

}
