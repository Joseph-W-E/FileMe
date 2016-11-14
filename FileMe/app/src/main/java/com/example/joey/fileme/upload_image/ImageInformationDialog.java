package com.example.joey.fileme.upload_image;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.joey.fileme.R;

/**
 * Created by Joey on 13-Nov-16.
 */

public class ImageInformationDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_image_information, null);

        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText etName = (EditText) view.findViewById(R.id.dialog_image_information_et_name);
                        EditText etDesc = (EditText) view.findViewById(R.id.dialog_image_information_et_desc);

                        if (etName.getText() != null && !etName.getText().toString().isEmpty()) {
                            String name = etName.getText().toString();
                            String desc = null;
                            if (etDesc.getText() != null && !etDesc.getText().toString().isEmpty())
                                desc = etDesc.getText().toString();
                            uploadData(name, desc);
                        }

                        getDialog().dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDialog().cancel();
                    }
                });

        return builder.create();
    }

    public void uploadData(String name, String desc) {
        UploadImageActivity activity = (UploadImageActivity) getActivity();
        activity.kickOffUpload(name, desc);
    }

}
