package org.mikolajczak.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
    final private static String STATE_SPINNER = "spinner";

    private RecyclerView recyclerView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "discoveryActivity onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        ThemoviedbApi.context = getApplicationContext();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ThemoviedbApi.setPosterImageSize(metrics.widthPixels);

        recyclerView = findViewById(R.id.movies_rv);
        MoviesAdapter moviesAdapter = new MoviesAdapter();

        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

        spinner = findViewById(R.id.spinner);
        TextView error_tv = findViewById(R.id.error_message);
        if(isInternetConnection() && ! ThemoviedbApi.isConfigured()) {
            error_tv.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);
            setupDb();
        } else if (! isInternetConnection() && ! ThemoviedbApi.isConfigured()){
            error_tv.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
            ThemoviedbApi.setActiveCategory(2);
        }


        // spinner setup
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.movies_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        if(savedInstanceState != null) {
            Log.d(TAG, "onCreate: restore savedInstanceState");
            spinner.setSelection(savedInstanceState.getInt(STATE_SPINNER));
        } else {
            Log.d(TAG, "onCreate: saveDinstanceState is NULL!");
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
        Log.d(TAG, "onResume: ONRESUME!!!");
        super.onResume();
        if (isInternetConnection() && ! ThemoviedbApi.isConfigured()) {
            setupDb();
        }
    }

    private boolean isInternetConnection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context
                .CONNECTIVITY_SERVICE);
        Log.d(TAG, "isInternetConnection: ThemoviedbApi.isConfigured: " + ThemoviedbApi.isConfigured());
        if(ThemoviedbApi.isConfigured() ||
                manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isAvailable() &&
                manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemSelected: position: " + position);
        ThemoviedbApi.setActiveCategory(position);
        RecyclerView recyclerView = findViewById(R.id.movies_rv);
        if(recyclerView.getAdapter() != null ) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onSaveInstanceState: ");
        savedInstanceState.putInt(STATE_SPINNER, spinner.getSelectedItemPosition());

        super.onSaveInstanceState(savedInstanceState);
    }
}
