package org.mikolajczak.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.mikolajczak.popularmovies.model.FavoritesContract;
import org.mikolajczak.popularmovies.utils.ThemoviedbApi;

public class DiscoveryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final private static String TAG  = "MOVIES";

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        recyclerView = findViewById(R.id.movies_rv);
        MoviesAdapter moviesAdapter = new MoviesAdapter();

        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

        if(isInternetConnection() && ! ThemoviedbApi.isConfigured()) {
            setupDb();
        }

        // spinner setup
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.movies_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        Cursor cursor = getContentResolver().query(FavoritesContract.FavoritesEntry.CONTENT_URI,null,null,null,null);

        String line = "";
        for (int i = 0 ; i < cursor.getColumnCount(); i++) {
            line += cursor.getColumnName(i) + "; ";
        }
        Log.d(TAG, "onCreate: columns: " + line );
        while(cursor.moveToNext()) {
            line = "";
            for (int i = 0 ; i < cursor.getColumnCount(); i++) {
                line += cursor.getString(i) + "; ";
            } Log.d(TAG, "onCreate: " + line );

        }
    }



    private void setupDb() {
        // movie db setup
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                ThemoviedbApi.retrievePopularMovies();
                ThemoviedbApi.retrieveTopratedMovies();

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                ThemoviedbApi.setConfigured();
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }.execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInternetConnection() && ! ThemoviedbApi.isConfigured()) {
            setupDb();
        }
    }

    private boolean isInternetConnection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context
                .CONNECTIVITY_SERVICE);
        TextView error_tv = findViewById(R.id.error_message);
        Log.d(TAG, "isInternetConnection: ThemoviedbApi.isConfigured: " + ThemoviedbApi.isConfigured());
        if(ThemoviedbApi.isConfigured() ||
                manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isAvailable() &&
                manager.getActiveNetworkInfo().isConnected()) {
            error_tv.setVisibility(View.GONE);
            return true;
        } else {
            error_tv.setVisibility(View.VISIBLE);
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
