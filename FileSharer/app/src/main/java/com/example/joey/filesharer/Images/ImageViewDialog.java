package com.example.joey.filesharer.Images;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.joey.filesharer.R;

/**
 * Created by Joey on 11-Nov-16.
 */

public class ImageViewDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_image, null);

        builder.setView(view);

        return builder.create();
    }

}
