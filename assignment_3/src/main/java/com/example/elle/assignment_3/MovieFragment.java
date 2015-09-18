package com.example.elle.assignment_3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.elle.assignment_3.Movie.*;

/**
 * Created by Elle on 2015-09-08.
 */

public class MovieFragment extends Fragment {

    ArrayList<Movie> mMoviesList = new ArrayList<>();
    MovieAdapter mAdapter;
    ProgressBar mProgressbar;
    Movie mMovie;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        mProgressbar = (ProgressBar) view.findViewById(R.id.movie_progress_bar);

        GridView gridview = (GridView) view.findViewById(R.id.movie_list_view);
        mAdapter = new MovieAdapter(mMoviesList, getActivity().getLayoutInflater());
        gridview.setAdapter(mAdapter);

        try{
            URL url;
            url = new URL("https://api-v2launch.trakt.tv/movies/popular?extended=images");
            downloadMoviesTask dmT = new downloadMoviesTask();
            dmT.execute(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return view;
    }


    private class downloadMoviesTask extends AsyncTask<URL, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(URL... params) {
            URL url = params[0];
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty ("Version", "2");
                String apiKey = "492a165927bfaff86b3030454939981d4e2d94c50515e15e42f41fbf57481a44";
                urlConnection.setRequestProperty("Authorization", apiKey);
                try {
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    return createListOfMovies(inputStream);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }


        private List<Movie> createListOfMovies(InputStream inputStream) throws IOException, JSONException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder();

            String line;
            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            mMovie = new Movie();
            String s = sb.toString();
            return mMovie.fromJSON(new JSONArray(s));
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            mMoviesList.addAll(movies);
            mAdapter.notifyDataSetChanged();
            mProgressbar.setVisibility(View.GONE);
        }
    }
}
