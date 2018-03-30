package org.mikolajczak.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.mikolajczak.popularmovies.model.Movie;
import org.mikolajczak.popularmovies.utils.ThemoviedbApi;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DiscoveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        ThemoviedbApi.setApiKey(getResources().getString(R.string.themoviedb_api_key));

/*
        final TextView tv = findViewById(R.id.textview);

        new AsyncTask(){
            @Override
            protected Object doInBackground(Object[] objects) {
                ArrayList<Movie> movies = ThemoviedbApi.getMovies(ThemoviedbApi.CATEGORY_POPULAR);

                return movies;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                ArrayList<Movie> movies = (ArrayList<Movie>) o;
                if(movies == null) {
                    tv.setText("Movies is null");
                } else {
                    for (Movie movie : movies) {
                        tv.append(movie.getTitle() + "\n");
                    }
                }
            }
        }.execute();
*/

        RecyclerView recyclerView = findViewById(R.id.movies_rv);
        MoviesAdapter moviesAdapter = new MoviesAdapter();

        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
