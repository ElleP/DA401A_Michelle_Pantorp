package com.example.elle.assignment_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
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

/**
 * Created by Elle on 2015-09-08.
 */

public class MovieAdapter extends BaseAdapter {

    //Proporties
    List<Movie> mMovieList;
    LayoutInflater mLayoutInflater;
    ImageView mCover;
    ProgressBar mProgressbar;

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

        convertView = mLayoutInflater.inflate(R.layout.movie_item, parent, false);

        //mProgressbar = (ProgressBar) convertView.findViewById(R.id.movie_progress_bar);

        Movie movie = (Movie) getItem(position);


        mCover = (ImageView) convertView.findViewById(R.id.movie_poster);


        TextView titleTextView = (TextView) convertView.findViewById(R.id.movie_title);
        titleTextView.setText(movie.getTitle());

        TextView YearTextView = (TextView) convertView.findViewById(R.id.movie_year);
        YearTextView.setText(movie.getYear());


        try{
            URL url;
            url = new URL(movie.getCoverUrl());
            new downloadCoversTask(mCover).execute(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return convertView;
    }

    private class downloadCoversTask extends AsyncTask<URL, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //mProgressbar.setVisibility(View.VISIBLE);
        }

        public downloadCoversTask(ImageView mCover) {

        }

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

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //mProgressbar.setVisibility(View.GONE);
            mCover.setImageBitmap(bitmap);
        }
    }
}
