package com.riyo_juan.mtvtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private OnItemClickListener onItemClickListener;

    // Interface to handle item click events
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    // Set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = (OnItemClickListener) listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getTitle());

        String genresText = String.join(", ", movie.getGenres());
        holder.genres.setText(genresText);

        // Load the image using Glide
        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster())
                .into(holder.poster);

        // Set the click listener for the movie item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView title, genres;
        public ImageView poster;

        public MovieViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.movie_title);
            genres = view.findViewById(R.id.movie_genres);
            poster = view.findViewById(R.id.movie_poster);
        }
    }
}
