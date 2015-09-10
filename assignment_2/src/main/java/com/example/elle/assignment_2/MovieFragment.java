package com.example.elle.assignment_2;

import android.content.res.TypedArray;;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Elle on 2015-09-08.
 */

public class MovieFragment extends Fragment {

    ArrayList<Movie> mMoviesList = new ArrayList<>();

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("MovieFragment", "inOnCreate");
        //Hämtar in arrayn med filmer
        TypedArray movies = getResources().obtainTypedArray(R.array.movies);

        for (int i = 0; i < movies.length(); i++ ){
            //Loopar igenom arrayn och för varje filmobjekt hämtas titel, år och poster ut och sparar i mMoviesList. Onödiga array tas sedan bort.
            TypedArray movieArray = getResources().obtainTypedArray(movies.getResourceId(i, 0));
            Movie movie = new Movie(movieArray.getString(0), movieArray.getString(1), movieArray.getString(2), movieArray.getResourceId(3,0), movieArray.getResourceId(4, 0));
            mMoviesList.add(movie);
            movieArray.recycle();
        }
        movies.recycle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("MovieFragment", "inOnCreateView");
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        GridView gridview = (GridView) view.findViewById(R.id.movie_list_view);
        gridview.setAdapter(new MovieAdapter(mMoviesList, getActivity().getLayoutInflater()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Log.i("MovieFragment", "in onSetItemClick");

                //Hämtar ut filmen som klickats på från mMoviesList och sedan läggs informationen om filmen in i en bundle som skickas till nästa fragment.
                Movie movie = mMoviesList.get(position);

                Bundle args = new Bundle();
                args.putString("movie_title", movie.title);
                args.putString("movie_year", movie.year);
                args.putString("movie_overview", movie.overview);
                args.putInt("movie_fanart", movie.fanart);

                Fragment toMovieItemFragment = new MovieItemFragment();
                toMovieItemFragment.setArguments(args);

                //Replacar nuvarande fragment med fragment för en film. Fragmentet läggs även till backStack så bakåtknappen ska fungera.
                getActivity()
                        .getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, toMovieItemFragment)
                        .addToBackStack("back")
                        .commit();
            }
        });

        return view;

    }


    }
