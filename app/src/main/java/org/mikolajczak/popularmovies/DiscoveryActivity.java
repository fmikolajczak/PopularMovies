package org.mikolajczak.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.mikolajczak.popularmovies.model.Movie;
import org.mikolajczak.popularmovies.utils.ThemoviedbApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.security.auth.callback.CallbackHandler;

public class DiscoveryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final private static String TAG  = "MOVIES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        ThemoviedbApi.setApiKey(getResources().getString(R.string.themoviedb_api_key));

        final RecyclerView recyclerView = findViewById(R.id.movies_rv);
        MoviesAdapter moviesAdapter = new MoviesAdapter();

        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

        // movie db setup
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

                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }.execute();


        // spinner setup
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.movies_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemSelected: position " + position);
        ThemoviedbApi.setActiveCategory(position);
        RecyclerView recyclerView = findViewById(R.id.movies_rv);
        if(recyclerView.getAdapter() != null ) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
