package com.example.elle.assignment_4;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeDialog extends DialogFragment implements Dialog.OnClickListener{


    public WelcomeDialog() {
        // Required empty public constructor
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Welcome to the Treasure Hunt")
                .setMessage("Go to the first marker on the map to start the treasure hunt. When the treasure hunt is started you will see a marker for the first challenge. When you reach the challenges you will get a notification sound and vibration and a question that you need to answer correctly otherwise the treasure hunt will restart. Follow all the markers until you've reached the last one. GOOD LUCK!")
                .setPositiveButton("I got it!", this);
        return dialog.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case Dialog.BUTTON_POSITIVE:
                break;
        }
    }
}
