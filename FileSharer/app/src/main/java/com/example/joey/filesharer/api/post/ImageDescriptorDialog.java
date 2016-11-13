package com.example.joey.filesharer.api.post;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.joey.filesharer.MainActivity;
import com.example.joey.filesharer.R;

/**
 * Created by Joey on 11-Nov-16.
 */

public class ImageDescriptorDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_image_descriptor, null);

        builder.setView(view)
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText etTitle = (EditText) view.findViewById(R.id.dialog_et_title);
                        EditText etDescr = (EditText) view.findViewById(R.id.dialog_et_description);

                        String title = etTitle.getText().toString();
                        String descr = etDescr.getText().toString();

                        uploadImage(title, descr);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDialog().cancel();
                    }
                });

        return builder.create();
    }

    private void uploadImage(String title, String description) {
        MainActivity activity = (MainActivity) getActivity();
        activity.uploadImage(title, description);
    }

}
