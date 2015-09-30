package com.example.elle.assignment_5;


import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;
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
import java.util.zip.Inflater;

import static com.example.elle.assignment_5.Movie.*;

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
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.movies_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getContext();
        CharSequence text = "About clicked";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        mProgressbar = (ProgressBar) view.findViewById(R.id.movie_progress_bar);

        final GridView gridview = (GridView) view.findViewById(R.id.movie_list_view);
        mAdapter = new MovieAdapter(mMoviesList, getActivity().getLayoutInflater());
        gridview.setAdapter(mAdapter);

        gridview.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        gridview.setMultiChoiceModeListener(new MultiChoiceModeListener() {


            public static final String TAG = "context_action_menu";

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_delete:
                        deleteSelectedItems();
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            private void deleteSelectedItems() {
                Log.i(TAG, "deleteSelectedItems");
                SparseBooleanArray checked = gridview.getCheckedItemPositions();
                for (int i = 0; i < checked.size(); i++) {
                    mMoviesList.remove(i);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                mode.setTitle(gridview.getCheckedItemCount() + " selected");
            }
        });


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
                urlConnection.setRequestProperty("Content-type", "application/json");
                urlConnection.setRequestProperty ("trakt-api-version", "2");
                String apiKey = "492a165927bfaff86b3030454939981d4e2d94c50515e15e42f41fbf57481a44";
                urlConnection.setRequestProperty("trakt-api-key", apiKey);
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
