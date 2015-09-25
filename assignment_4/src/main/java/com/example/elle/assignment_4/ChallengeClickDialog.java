package com.example.elle.assignment_4;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class ChallengeClickDialog extends DialogFragment {
    MapsActivity mMapsActivity;

    private static final String TAG = "MapsActivity" ;
    public ChallengeClickDialog() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mMapsActivity = (MapsActivity) getActivity();

        final Question mItem = MapsActivity.mQuestionsList.get(mMapsActivity.mIndex);
        CharSequence[] choicesList = {mItem.choice_one, mItem.choice_two, mItem.choice_three};

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                .setTitle(mItem.question)
                .setSingleChoiceItems(choicesList, -1, null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedPos = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        if (selectedPos == mItem.answer){
                            mMapsActivity.mIndex++;
                            mMapsActivity.mIndexM++;
                            mMapsActivity.onUserSelectValue(true);
                            dialog.dismiss();
                        }else{
                            mMapsActivity.onUserSelectValue(false);
                            dialog.dismiss();
                        }
                    }
                });

        return dialog.create();
    }


}
