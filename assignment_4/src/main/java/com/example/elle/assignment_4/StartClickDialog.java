package com.example.elle.assignment_4;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;



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
                .setPositiveButton("OK, I'm up for it!", this);
                //.setNegativeButton("Noooooo", this);
        return dialog.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        MapsActivity mMapsActivity = (MapsActivity) getActivity();
        mMapsActivity.mIndexM++;
        switch (which){
            case Dialog.BUTTON_POSITIVE:
                break;

            //Didn't get det button_negative to work - messed up some other flow in the app. Couldn´t pinpoint the errors origin
            /*case Dialog.BUTTON_NEGATIVE:
                mMapsActivity.mIndexM = 0;
                mMapsActivity.mIndex= 0;
                mMapsActivity.mPositions.get(mMapsActivity.mIndex).setVisible(true);
                mMapsActivity.mPositions.get(mMapsActivity.mIndex+1).setVisible(false);*/


                //No action when this is clicked except for the dialog closes - should be som action

        }
        dialog.dismiss();
    }
}
