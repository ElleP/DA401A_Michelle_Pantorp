package com.example.elle.assignment_2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Elle on 2015-09-08.
 */

public class MovieAdapter extends BaseAdapter {

    //Proporties
    List<Movie> mMovieList;
    LayoutInflater mLayoutInflater;

    //Konstruktorn
    public MovieAdapter(List<Movie> mMovieList, LayoutInflater mLayoutInflater) {
        this.mMovieList = mMovieList;
        this.mLayoutInflater = mLayoutInflater;
    }

    @Override
    public int getCount() {

        return mMovieList.size();
    }

    @Override
    public Object getItem(int position) {

        return mMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("MovieAdapter", "OnGetView");

        convertView = mLayoutInflater.inflate(R.layout.movie_item, parent, false);

        Movie movie = (Movie) getItem(position);

        ImageView posterImageView = (ImageView) convertView.findViewById(R.id.movie_poster);
        posterImageView.setImageResource(movie.poster);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.movie_title);
        titleTextView.setText(movie.title);

        TextView YearTextView = (TextView) convertView.findViewById(R.id.movie_year);
        YearTextView.setText(movie.year);

        return convertView;
    }
}
