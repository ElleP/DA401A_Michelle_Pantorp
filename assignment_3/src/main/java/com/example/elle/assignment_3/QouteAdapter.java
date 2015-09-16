package com.example.elle.assignment_3;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Elle on 2015-09-15.
 */
public class QouteAdapter extends BaseAdapter {

    List<String> mQuote;
    LayoutInflater mLayoutInflater;

    public QouteAdapter(List<String> mQuote, LayoutInflater mLayoutInflater) {
        this.mQuote = mQuote;
        this.mLayoutInflater = mLayoutInflater;
    }

    @Override
    public int getCount() {
        return mQuote.size();
    }

    @Override
    public Object getItem(int position) {
        return mQuote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(R.layout.quote_item, parent, false);
        Log.i("Adapter", "inOnQuoteAdapter");
        String quote = mQuote.get(0);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.quote);
        titleTextView.setText(quote);



        return convertView;
    }
}
