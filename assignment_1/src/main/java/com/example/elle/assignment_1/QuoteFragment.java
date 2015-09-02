package com.example.elle.assignment_1;


import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuoteFragment extends Fragment implements View.OnClickListener{


    public QuoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("QuoteFragment", "In onCreateView");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_quote, container, false);
        View button = v.findViewById(R.id.quote_button_id);
        button.setOnClickListener(this);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("QuoteFragment", "In onCreate");
    }

    @Override
    public void onClick(View v) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, new ShowQuoteFragment())
                .addToBackStack("back")
                .commit();
    }
}
