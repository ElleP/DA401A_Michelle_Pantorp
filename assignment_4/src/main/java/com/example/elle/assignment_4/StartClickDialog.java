package com.example.elle.assignment_4;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartClickDialog extends DialogFragment implements Dialog.OnClickListener {


    public StartClickDialog() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Treasure Hunt")
                .setMessage("This is where the treasure hunt starts. Good Luck!")
                .setPositiveButton("OK, I'm up for it!", this)
                .setNegativeButton("Noooooo", this);
        return dialog.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        MapsActivity mMapsActivity = (MapsActivity) getActivity();
        mMapsActivity.mIndexM++;
        switch (which){
            case Dialog.BUTTON_POSITIVE:
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }
    }
}
