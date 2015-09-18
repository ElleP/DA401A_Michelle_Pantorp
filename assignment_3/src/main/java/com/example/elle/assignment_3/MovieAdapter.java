package com.example.elle.assignment_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Elle on 2015-09-08.
 */

public class MovieAdapter extends BaseAdapter {

    //Proporties
    List<Movie> mMovieList;
    LayoutInflater mLayoutInflater;
    ImageView mCover;
    Bitmap mBitmap;

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

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.movie_item, parent, false);
        }

        Movie movie = (Movie) getItem(position);
        try {
            URL url;
            url = new URL(movie.getCoverUrl());
            downloadCoversTask dst = new downloadCoversTask();
            mBitmap = dst.execute(url).get();

        } catch (MalformedURLException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        mCover = (ImageView) convertView.findViewById(R.id.movie_poster);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.movie_title);
        TextView YearTextView = (TextView) convertView.findViewById(R.id.movie_year);

        titleTextView.setText(movie.getTitle());
        YearTextView.setText(movie.getYear());
        mCover.setImageBitmap(mBitmap);

        return convertView;
    }

    private class downloadCoversTask extends AsyncTask<URL, Void, Bitmap> {

        protected Bitmap doInBackground(URL... params) {
            URL url = params[0];

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream((urlConnection).getInputStream());
                    return BitmapFactory.decodeStream(in);

                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
