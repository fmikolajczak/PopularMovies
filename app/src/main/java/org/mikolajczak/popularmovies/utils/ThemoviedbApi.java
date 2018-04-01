package org.mikolajczak.popularmovies.utils;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mikolajczak.popularmovies.model.Movie;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
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
    final static String API_KEY_PARAM = "api_key";
    final static String API_PAGE_PARAM = "page";
    private static final int THRESHOLD = 20;
    private static String API_KEY;

    private static ArrayList<Movie> moviesPopular = new ArrayList<>();
    private static ArrayList<Movie> moviesToprated = new ArrayList<>();

    private static int activeCategory = 0 ;

    private static int popularPage = 0;
    private static int topratedPage = 0;

    private static boolean loading = false;

    final private static String TAG = "MOVIES";

    public static void setApiKey(String key) {
        ThemoviedbApi.API_KEY = key;
    }

    public static void retrieveMoviesOnBackgroundThread() {
        if ( activeCategory == 0) {
            retrievePopularMoviesOnBackgroundThread();
        } else {
            retrieveTopratedMoviesOnBackgroundThread();
        }
     }

    public static void retrievePopularMovies() {
        popularPage++;
        moviesPopular.addAll(getMovies(CATEGORY_POPULAR, popularPage));
    }

    public static void retrievePopularMoviesOnBackgroundThread() {
        loading = true;
        new AsyncTask(){
            @Override
            protected Object doInBackground(Object[] objects) {
                retrievePopularMovies();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                loading = false;
            }
        }.execute();

    }

    public static void retrieveTopratedMovies() {
        topratedPage++;
        moviesToprated.addAll(getMovies(CATEGORY_TOPRATED, topratedPage));
    }

    public static  void retrieveTopratedMoviesOnBackgroundThread() {
        loading = true;
        new AsyncTask(){
            @Override
            protected Object doInBackground(Object[] objects) {
                retrieveTopratedMovies();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                loading = false;
            }
        }.execute();
    }

        /**
         * return list of movies according to apiCategory
         * @param category CATEGORY_POPULAR CATEGORY_TOPRATED
         * @return
         */
    public static ArrayList<Movie> getMovies(String category, int page) {
        URL url = buildUrl(category, page);

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


    private static URL buildUrl(String path, int page) {
        Uri buildUri = Uri.parse(API_BASE_URL)
                .buildUpon()
                .appendEncodedPath(path)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(API_PAGE_PARAM, String.valueOf(page))
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

    public static Movie getMovie(int index) {
        if (activeCategory == 0) {
            if(index > moviesPopular.size() - THRESHOLD && ! loading) {
                retrievePopularMoviesOnBackgroundThread();
            }
            return moviesPopular.get(index);
        } else if (activeCategory == 1) {
            if(index > moviesToprated.size() - THRESHOLD && ! loading) {
                retrieveTopratedMoviesOnBackgroundThread();
            }
            return moviesToprated.get(index);
        }
        return null;
    }


    public static int getActiveCategory() {
        return activeCategory;
    }

    public static void setActiveCategory(int activeCategory) {
        ThemoviedbApi.activeCategory = activeCategory;
    }

    public static int getCount() {
        if (activeCategory ==0) {
            return moviesPopular.size();
        } else {
            return moviesToprated.size();
        }
    }
}
