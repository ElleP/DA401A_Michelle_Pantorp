package com.example.elle.assignment_5;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elle on 2015-09-08.
 */


public class Movie {
    private String year;
    private String title;
    public String coverUrl;
    private List<Movie> movieList;

    public Movie(){

    }

    public Movie(String title, String year, String coverUrl) {
        this.year = year;
        this.title = title;
        this.coverUrl = coverUrl;
    }

    public String getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public List<Movie> fromJSON(JSONArray jsonArray) throws JSONException {
        movieList = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            title = jsonArray.getJSONObject(i).getString("title");
            year = jsonArray.getJSONObject(i).getString("year");
            coverUrl = jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("poster").getString("thumb");
            movieList.add(new Movie(title, year, coverUrl));
        }

        return movieList;
    }
}

