package org.mikolajczak.popularmovies;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.mikolajczak.popularmovies.model.Movie;
import org.mikolajczak.popularmovies.utils.ThemoviedbApi;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    final static String TAG = "MOVIES";

    MoviesAdapter() {
        super();
        Log.d(TAG, "MoviesAdapter: ");
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem  = R.layout.item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutIdForItem, parent, shouldAttachToParentImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(ThemoviedbApi.getPopularMovies() != null) {
            return ThemoviedbApi.getPopularMovies().size();
        } else {
            return 0;
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        View view;

        public MovieViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textView = itemView.findViewById(R.id.debug_tv);
            imageView = itemView.findViewById(R.id.image_view);
        }

        void bind(int listIndex) {
            Log.d(TAG, "Movieholder bind: " + String.valueOf(listIndex));
            ArrayList<Movie> movie = ThemoviedbApi.getPopularMovies();
            textView.setText(movie.get(listIndex).getTitle());
            Context context = view.getContext();

            String posterPath = context.getResources().getString(R.string.image_base_url) +
                    movie.get(listIndex).getPoster();
            Picasso.with(context)
                    .load(posterPath)
                    .into(imageView);
            Log.d(TAG, "posterPath: " + posterPath);
        }
    }
}
