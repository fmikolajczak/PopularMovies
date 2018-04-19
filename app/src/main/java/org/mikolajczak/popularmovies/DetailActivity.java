package org.mikolajczak.popularmovies;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.mikolajczak.popularmovies.model.FavoritesContract;
import org.mikolajczak.popularmovies.model.Movie;
import org.mikolajczak.popularmovies.utils.ThemoviedbApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG =  "PM DetailA";

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Movie movie;
    private boolean isFavorite;

    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.poster_iv) ImageView imageVi;
    @BindView(R.id.release_date_tv) TextView releaseDateTv;
    @BindView(R.id.vote_tv) TextView voteTv;
    @BindView(R.id.plot_tv) TextView plotTv;
    @BindView(R.id.favorite_cb) CheckBox favoriteCb;
    @BindView(R.id.trailers_layout) LinearLayout trailersLayout;
    @BindView(R.id.reviews_layout) LinearLayout reviewsLayout;

    private TextView trailersTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if(intent == null) {
            CloseOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            CloseOnError();
            return;
        }

        ButterKnife.bind(this);

        movie = ThemoviedbApi.getMovie(position);

        Uri uri = FavoritesContract.FavoritesEntry.CONTENT_URI.buildUpon().appendPath(
                String.valueOf(movie.getMoviedbId())).build();
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor != null) {
            Log.d(TAG, "onCreate: cursor.getCount: " + cursor.getCount());
            if (cursor.getCount() > 0) {
                isFavorite = true;
            } else {
                isFavorite = false;
            }
        }

        trailersTv = new TextView(this);
        trailersLayout.addView(trailersTv);

        if (movie != null) {
            setTitle(movie.getTitle());
            populateUI(movie);
            loadTrailers();
            loadReviews();
        }
    }

    private void loadReviews() {
        //TODO
        new AsyncTask(){
            @Override
            protected Object doInBackground(Object[] objects) {
                return ThemoviedbApi.getTrailersYoutubeIds(movie.getMoviedbId());
            }

            @Override
            protected void onPostExecute(Object o) {
                ArrayList<String> ids = (ArrayList) o;
                for(String id : ids) {
                    trailersTv.append(id);
                }
            }
        }.execute();
    }

    private void loadTrailers() {
        //TODO
    }

    private void populateUI(Movie movie) {
        Picasso.with(this)
                .load(getResources().getString(R.string.image_big_url) + movie.getPoster())
                .into(imageVi);
        imageVi.setContentDescription(getResources().getString(R.string.poster_description_prefix) +
                movie.getTitle());
        titleTv.setText(movie.getTitle());
        releaseDateTv.setText(movie.getReleaseDate());
        voteTv.setText(String.valueOf(movie.getVoteAvg()));
        plotTv.setText(movie.getPlotSynopsis());
        favoriteCb.setChecked(isFavorite);
    }

    private void CloseOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    public void onClickFavoriteCb(View view) {
        CheckBox cb = (CheckBox) view;
        Log.d(TAG, "onClickFavoriteCb: isChecked: " + cb.isChecked());
        if(cb.isChecked()) {
            // TODO: add to favorites
            ContentValues values = new ContentValues();
            values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIEDB_ID, movie.getMoviedbId());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_PLOT, movie.getPlotSynopsis());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_POSTER, movie.getPoster());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_RELEASE, movie.getReleaseDate());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_TITLE, movie.getTitle());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_VOTE, movie.getVoteAvg());

            Uri uri = getContentResolver().insert(FavoritesContract.FavoritesEntry.CONTENT_URI, values);

            if(uri != null) {
                Log.d(TAG, "onClickFavoriteCb: uri: " + uri);
            }
        } else {
            Uri uri = FavoritesContract.FavoritesEntry.CONTENT_URI.buildUpon().appendPath(String
                    .valueOf(movie.getMoviedbId())).build();
            int count = getContentResolver().delete(uri, null, null);
            Log.d(TAG, "onClickFavoriteCb: deleted count: " + count);
        }
    }
}
