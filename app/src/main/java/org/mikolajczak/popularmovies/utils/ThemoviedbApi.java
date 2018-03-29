package org.mikolajczak.popularmovies.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mikolajczak.popularmovies.model.Movie;

import java.net.URL;
import java.util.ArrayList;

/**
 * Retrive data from themoviedb.org
 */
public class ThemoviedbApi {
    final String API_DOMAIN = "api.themoviedb.org";
    final String API_POPULAR_URL = "/movie/popular";
    final String API_RATED_URL = "/movie/top_rated";

    public static ArrayList<Movie> getMoviesFromUrl(URL url, String apiUrlCategory) {
        // TODO
        return null;
    }

    public static ArrayList<Movie> getMostPopular() {
        // TODO
        return null;
    }

    public static ArrayList<Movie> getHighestRated() {
        // TODO
        return null;
    }

    static ArrayList<Movie> getMoviesFromJson(String json) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        JSONArray resultsJsonArray;
        try {
            resultsJsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<Movie> movies = new ArrayList<>();
        JSONObject jsonMovie;
        Movie movie;
        try {
            for (int i = 0; i < resultsJsonArray.length(); i++) {
                jsonMovie = resultsJsonArray.getJSONObject(i);

                String poster = jsonMovie.getString("poster_path");
                String title = jsonMovie.getString("original_title");
                String releaseDate = jsonMovie.getString("release_date");
                Double voteAvg = jsonMovie.getDouble("vote_average");
                String plotSynopsis = jsonMovie.getString("overview");

                movie = new Movie(poster, title, releaseDate, voteAvg, plotSynopsis);
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return movies;
    }
}
