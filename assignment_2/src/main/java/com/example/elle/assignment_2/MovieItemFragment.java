package com.example.elle.assignment_2;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieItemFragment extends Fragment {
    private ArrayList<Movie> mMovieList = new ArrayList<Movie>();

    public MovieItemFragment() {
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


        Bundle movie = getArguments();

        Log.i("MovieItemFragment", "inOnCreateView");
        View view = inflater.inflate(R.layout.fragment_movie_item, container, false);


        TextView titleTextView = (TextView) view.findViewById(R.id.movie_title);
        titleTextView.setText(movie.getString("movie_title"));

        TextView yearTextView = (TextView) view.findViewById(R.id.movie_year);
        yearTextView.setText(movie.getString("movie_year"));

        TextView overviewTextView = (TextView) view.findViewById(R.id.movie_overview);
        overviewTextView.setText(movie.getString("movie_overview"));

        ImageView fanartImageView = (ImageView) view.findViewById((R.id.movie_fanart));
        fanartImageView.setImageResource(movie.getInt("movie_fanart"));

        return view;



    }


}
