package org.mikolajczak.popularmovies;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.mikolajczak.popularmovies.model.Movie;
import org.mikolajczak.popularmovies.utils.ThemoviedbApi;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    final static String TAG = "MOVIES";
    private int itemCount = -1;


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
        int count = ThemoviedbApi.getCount();
        if (count > itemCount) {
            itemCount = count;
        }
        return count;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener {
        TextView textView;
        ImageView imageView;
        View view;

        public MovieViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textView = itemView.findViewById(R.id.debug_tv);
            imageView = itemView.findViewById(R.id.image_view);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            Movie movie = ThemoviedbApi.getMovie(listIndex);

            if(movie != null) {
                textView.setText(movie.getTitle());
                Context context = view.getContext();
                String posterPath = context.getResources().getString(R.string.image_base_url) + movie.getPoster();
                Picasso.with(context).load(posterPath).into(imageView);
            }
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_POSITION, getLayoutPosition());
            v.getContext().startActivity(intent);
        }
    }


}
