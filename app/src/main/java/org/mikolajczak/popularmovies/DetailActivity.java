package org.mikolajczak.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.mikolajczak.popularmovies.model.Movie;
import org.mikolajczak.popularmovies.utils.ThemoviedbApi;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.poster_iv) ImageView imageVi;
    @BindView(R.id.release_date_tv) TextView releaseDateTv;
    @BindView(R.id.vote_tv) TextView voteTv;
    @BindView(R.id.plot_tv) TextView plotTv;

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

        Movie movie = ThemoviedbApi.getMovie(position);
        if (movie != null) {
            setTitle(movie.getTitle());
            populateUI(movie);
        }
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
    }

    private void CloseOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
