package org.mikolajczak.popularmovies.utils;


import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mikolajczak.popularmovies.model.Movie;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Retrieve data from themoviedb.org
 */
public class ThemoviedbApi {
    final public static String CATEGORY_POPULAR = "movie/popular";
    final public static String CATEGORY_TOPRATED = "movie/top_rated";

    final static String API_BASE_URL = "https://api.themoviedb.org/3";
    final static String API_PARAM = "api_key";
    private static String API_KEY;

    private static ArrayList<Movie> moviesPopular;
    private static ArrayList<Movie> moviesToprated;

    final private static String TAG = "MOVIES";

    public static void setApiKey(String key) {
        ThemoviedbApi.API_KEY = key;
    }

    public static ArrayList<Movie> retrievePopularMovies() {
        if (moviesPopular == null) {
            Log.d(TAG, "getPopularMovies: retrieve from network");
            moviesPopular = getMovies(CATEGORY_POPULAR);
        }
        return moviesPopular;
    }

    public static ArrayList<Movie> retrieveTopratedMovies() {
        if (moviesToprated == null) {
            Log.d(TAG, "getTopratedMovies: retrieve from network");
            moviesToprated = getMovies(CATEGORY_TOPRATED);
        }
        return moviesToprated;
    }

    public static ArrayList<Movie> getPopularMovies() {
        return moviesPopular;
    }

    public static ArrayList<Movie> getTopratedMovies() {
        return moviesToprated;
    }


        /**
         * return list of movies according to apiCategory
         * @param category CATEGORY_POPULAR CATEGORY_TOPRATED
         * @return
         */
    public static ArrayList<Movie> getMovies(String category) {
        URL url = buildUrl(category);

        String json;
        try {
            json = getUrlContent(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return getMoviesFromJson(json);
    }

    public static String getUrlContent(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


    private static URL buildUrl(String path) {
        Uri buildUri = Uri.parse(API_BASE_URL)
                .buildUpon()
                .appendEncodedPath(path)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
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
