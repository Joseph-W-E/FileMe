package com.example.joey.filesharer.api.get;

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
 * Created by Joey on 13-Nov-16.
 */

public class ImageNameDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_image_name, null);

        builder.setView(view)
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText etTitle = (EditText) view.findViewById(R.id.dialog_et_search_image_title);

                        String title = etTitle.getText().toString();

                        uploadImage(title);
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

    private void uploadImage(String title) {
        MainActivity activity = (MainActivity) getActivity();
        activity.getImage(title);
    }
}
