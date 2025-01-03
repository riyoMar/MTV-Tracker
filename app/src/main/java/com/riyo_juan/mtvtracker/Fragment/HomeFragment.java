package com.riyo_juan.mtvtracker.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riyo_juan.mtvtracker.Activity.MovieDetailsActivity;
import com.riyo_juan.mtvtracker.Movie;
import com.riyo_juan.mtvtracker.MovieAdapter;
import com.riyo_juan.mtvtracker.MovieResponse;
import com.riyo_juan.mtvtracker.MoviesApi;
import com.riyo_juan.mtvtracker.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private MoviesApi moviesApi;
    private boolean isLoading = false;
    private static final String TAG = "HomeFragment";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://moviesapi.ir/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Initialize API interface
//        moviesApi = retrofit.create(MoviesApi.class);

        // Initialize the movie adapter
        movieAdapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(movieAdapter);


        movieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra("movie_id", movie.getId());  // Pass the movie_id to the new activity
                startActivity(intent);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading && layoutManager.findLastVisibleItemPosition() == movieList.size() - 1) {
                    // Load next page when scrolled to bottom
                    fetchAllMovies();
                }
            }
        });

        moviesApi = retrofit.create(MoviesApi.class);

        fetchAllMovies();

        return view;
    }

    private void fetchAllMovies() {
        int currentPage = 1;
        isLoading = true;

        // initialize the movie list
        movieList.clear();

        // Fetch movies for the first page
        loadMovies(currentPage);
    }

    private void loadMovies(final int page) {
        Call<MovieResponse> call = moviesApi.getMovies(page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.d(TAG, response.body().toString());
                if (response.isSuccessful() && response.body() != null) {
                    MovieResponse movieResponse = response.body();
                    List<Movie> movies = movieResponse.getData();

                    // Add movies to the list
                    movieList.addAll(movies);

                    // Notify the adapter if it's the first page, or just append for subsequent pages
                    if (movieAdapter == null) {
                        movieAdapter = new MovieAdapter(movieList);
                        recyclerView.setAdapter(movieAdapter);
                    } else {
                        movieAdapter.notifyDataSetChanged();
                    }


                    // Check if there are more pages and load them
                    if (movies.size() > 0) {
//                    if (!movies.isEmpty()) {
                        // Increase page number and fetch next page
                        loadMovies(page + 1);
                    } else {
                        isLoading = false;
                    }
                } else {
                    // Handle API failure or empty data
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Handle failure here
                isLoading = false;
                t.printStackTrace();
            }
        });
    }

}