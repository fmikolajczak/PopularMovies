package org.mikolajczak.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
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
        return 20;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.debug_tv);
            imageView = itemView.findViewById(R.id.image_view);
        }

        void bind(int listIndex) {
            textView.setText(String.valueOf(listIndex));
        }
    }
}
