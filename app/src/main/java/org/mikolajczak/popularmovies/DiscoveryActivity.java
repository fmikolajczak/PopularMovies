package org.mikolajczak.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.mikolajczak.popularmovies.model.Movie;
import org.mikolajczak.popularmovies.utils.ThemoviedbApi;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DiscoveryActivity extends AppCompatActivity {  
    final private static String TAG  = "MOVIES";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        ThemoviedbApi.setApiKey(getResources().getString(R.string.themoviedb_api_key));

        new AsyncTask(){
            @Override
            protected Object doInBackground(Object[] objects) {
                Log.d(TAG, "doInBackground: execute getPopularMovies()");
                ThemoviedbApi.retrievePopularMovies();
                Log.d(TAG, "doInBackground: execute getTopratedMovies()");
                ThemoviedbApi.retrieveTopratedMovies();

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                RecyclerView recyclerView = findViewById(R.id.movies_rv);
                MoviesAdapter moviesAdapter = new MoviesAdapter();

                recyclerView.setAdapter(moviesAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            }
        }.execute();

    }
}
