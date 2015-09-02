package com.example.elle.assignment_1;


import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowQuoteFragment extends Fragment {


private String[] quoteArray;
    public ShowQuoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ShowQuoteFragment", "In onCreateView");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show_quote, container, false);

        //Gets date from helpclass and manipulates textview in fragment
        String currentDate = Helpers.getDate();
        TextView date = (TextView) v.findViewById(R.id.current_date);
        date.setText((CharSequence) currentDate);

        //Gets quotes from strings-file and gets a quote from helpclass and then manipulates textview in fragment
        //Kollade lite på detta o detta är inte helsnyggt att hämta arrayen här.
        //Men som du märkt behöver man ha Context för att använda metoden getResources.
        //Jag tycker man skall deklarera en static variabel i Helpers samt tilldela den ett
        //värde i onCreate i MainActivity, alternativet är att ha en referens till Context i Helpers

        String [] quoteArray = getResources().getStringArray(R.array.quote_array);
        String randomQuote = Helpers.getQuote(quoteArray);
        TextView t = (TextView) v.findViewById(R.id.quote_text_id);
        t.setText(randomQuote);

        //Returns view
        return v;
    }



}