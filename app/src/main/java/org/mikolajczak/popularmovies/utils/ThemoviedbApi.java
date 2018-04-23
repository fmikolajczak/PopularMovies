package org.mikolajczak.popularmovies.utils;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mikolajczak.popularmovies.BuildConfig;
import org.mikolajczak.popularmovies.model.FavoritesContract;
import org.mikolajczak.popularmovies.model.Movie;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Retrieve data from themoviedb.org
 */
public class ThemoviedbApi {
    final public static String CATEGORY_POPULAR = "movie/popular";
    final public static String CATEGORY_TOPRATED = "movie/top_rated";
    private static final String API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY;

    final static String API_BASE_URL = "https://api.themoviedb.org/3";
    final static String API_KEY_PARAM = "api_key";
    final static String API_PAGE_PARAM = "page";
    final static String API_MOVIE_PATH = "movie";
    final static String API_VIDEOS_PATH = "videos";
    final static String API_REVIEWS_PATH = "reviews";

    private static final int THRESHOLD = 20;

    private static ArrayList<Movie> moviesPopular = new ArrayList<>();
    private static ArrayList<Movie> moviesToprated = new ArrayList<>();
    private static ArrayList<Movie> moviesFavorites = new ArrayList<>();

    private static int activeCategory = 0 ;

    private static int popularPage = 0;
    private static int topratedPage = 0;

    private static boolean loading = false;

    final private static String TAG = "MOVIES";
    private static boolean configured = false;

    public static Context context;
    private static String posterWidthDiscovery;
    private static String posterWidthDetails;

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
         * @return list of movies
         */
    public static ArrayList<Movie> getMovies(String category, int page) {
        URL url = buildMovieListUrl(category, page);

        String json;
        try {
            json = getUrlContent(url);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
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


    private static URL buildMovieListUrl(String path, int page) {
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

    private static URL buildMovieDetailUrl(int moviedbId, String itemType) {
        Uri buildUri = Uri.parse(API_BASE_URL)
                .buildUpon()
                .appendEncodedPath(API_MOVIE_PATH)
                .appendEncodedPath(String.valueOf(moviedbId))
                .appendEncodedPath(itemType)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static ArrayList<Movie> getMoviesFromJson(String json) {
        ArrayList<Movie> movies = new ArrayList<>();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return movies;
        }

        JSONArray resultsJsonArray;
        try {
            resultsJsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
            return movies;
        }


        JSONObject jsonMovie;
        Movie movie;
        try {
            for (int i = 0; i < resultsJsonArray.length(); i++) {
                jsonMovie = resultsJsonArray.getJSONObject(i);

                int id = jsonMovie.getInt("id");
                String poster = jsonMovie.getString("poster_path");
                String title = jsonMovie.getString("original_title");
                String releaseDate = jsonMovie.getString("release_date");
                Double voteAvg = jsonMovie.getDouble("vote_average");
                String plotSynopsis = jsonMovie.getString("overview");

                movie = new Movie(poster, title, releaseDate, voteAvg, plotSynopsis, id);
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
        } else if (activeCategory == 2) {
            return moviesFavorites.get(index);
        }
        return null;
    }


    public static int getActiveCategory() {
        return activeCategory;
    }

    public static void setActiveCategory(int activeCategory) {
        ThemoviedbApi.activeCategory = activeCategory;
        if(activeCategory == 2) {
            Cursor cursor = context.getContentResolver().query(FavoritesContract.FavoritesEntry.CONTENT_URI, null,null,null,null);
            Movie movie;
            ArrayList<Movie> moviesList = new ArrayList<>();
            while (cursor.moveToNext()) {
                movie = new Movie(
                        cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_POSTER)),
                        cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_RELEASE)),
                        cursor.getDouble(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_VOTE)),
                        cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_PLOT)),
                        cursor.getInt(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_MOVIEDB_ID))
                        );
                moviesList.add(movie);
            }
            moviesFavorites = moviesList;
        }
    }

    public static int getCount() {
        if (activeCategory ==0) {
            return moviesPopular.size();
        } else if (activeCategory == 1 ){
            return moviesToprated.size();
        } else if (activeCategory == 2){
            return moviesFavorites.size();
        } else {
            return 0;
        }
    }

    public static void setConfigured() {
        configured = true;
    }

    public static boolean isConfigured(){
        return configured;
    }

    /*
     * https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent
     */
    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public static List<String> getTrailersYoutubeIds(int moviedbId) {
        URL url = buildMovieDetailUrl(moviedbId, API_VIDEOS_PATH);
        String jsonString;
        try {
            jsonString = getUrlContent(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        List<String> trailersYoutubeIds = getYoutubeIdsFromJson(jsonString);
        return  trailersYoutubeIds;
    }

    private static List<String> getYoutubeIdsFromJson(String jsonString) {
        Log.d(TAG, "getYoutubeIdsFromJson: jsonString: " + jsonString);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
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

        JSONObject jsonVideo;
        ArrayList<String> ids = new ArrayList<>();
        try {
            for (int i = 0; i < resultsJsonArray.length(); i++) {
                jsonVideo = resultsJsonArray.getJSONObject(i);
                if(jsonVideo.getString("type").equals("Trailer") &&
                        jsonVideo.getString("site").equals("YouTube")) {
                    Log.d(TAG, "getYoutubeIdsFromJson: jsonVideo added: " + jsonVideo);
                    ids.add(jsonVideo.getString("key"));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "getYoutubeIdsFromJson: ids: " + ids);
        return ids;
    }

    public static Object getReviews(int moviedbId) {
        URL url = buildMovieDetailUrl(moviedbId, API_REVIEWS_PATH);
        String jsonString;
        try {
            jsonString = getUrlContent(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        List<String[]> reviews = getReviewsFromJson(jsonString);
        return  reviews;
    }

    private static JSONArray getResultArrayFromJson(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
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
        return resultsJsonArray;
    }
    private static List<String[]> getReviewsFromJson(String jsonString) {
        JSONArray resultsJsonArray = getResultArrayFromJson(jsonString);

        JSONObject jsonReview;
        ArrayList<String[]> reviews = new ArrayList<>();
        try {
            String [] review;
            for (int i = 0; i < resultsJsonArray.length(); i++) {
                jsonReview = resultsJsonArray.getJSONObject(i);
                review = new String[]{
                        jsonReview.getString("author"),
                        jsonReview.getString("content"),
                        jsonReview.getString("id"),
                        jsonReview.getString("url"),
                };
                reviews.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public static void setPosterImageSize(int displayWidth)  {
        int[] availableWidths =  { 92, 154, 185, 342, 500, 780 };
        int discoveryWidth = availableWidths[0];
        int detailsWidth = availableWidths[0];
        for (int i = 1; i < availableWidths.length ; i++ ) {
            if(availableWidths[i] * 3 < displayWidth) {
                discoveryWidth = availableWidths[i];
            }
            if(availableWidths[i] < displayWidth) {
                detailsWidth = availableWidths[i];
            }
        }
        posterWidthDiscovery = "w" + discoveryWidth;
        posterWidthDetails = "w" + detailsWidth;
    }

    public static String getPosterWidthDiscovery() {
        return  posterWidthDiscovery;
    }

    public static String getPosterWidthDetails() {
        return posterWidthDetails;
    }
}
