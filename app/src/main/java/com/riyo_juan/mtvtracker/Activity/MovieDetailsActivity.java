package com.riyo_juan.mtvtracker.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.riyo_juan.mtvtracker.ApiClient;
import com.riyo_juan.mtvtracker.MovieDetailsResponse;
import com.riyo_juan.mtvtracker.MoviesApi;
import com.riyo_juan.mtvtracker.R;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView titleTextView, plotTextView, releasedTextView, runtimeTextView,
            directorTextView, writerTextView, actorsTextView, countryTextView,
            imdbRatingTextView, typeTextView;
    private ImageView posterImageView;

    private MoviesApi moviesApi;

    private static final String TAG = "MovieDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_details);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        // Initialize views
        titleTextView = findViewById(R.id.titleTextView);
        plotTextView = findViewById(R.id.plotTextView);
        releasedTextView = findViewById(R.id.releasedTextView);
        runtimeTextView = findViewById(R.id.runtimeTextView);
        directorTextView = findViewById(R.id.directorTextView);
        writerTextView = findViewById(R.id.writerTextView);
        actorsTextView = findViewById(R.id.actorsTextView);
        countryTextView = findViewById(R.id.countryTextView);
        imdbRatingTextView = findViewById(R.id.imdbRatingTextView);
        typeTextView = findViewById(R.id.typeTextView);
        posterImageView = findViewById(R.id.posterImageView);

        // Initialize API
        moviesApi = ApiClient.getClient().create(MoviesApi.class);

        // Get movie_id from intent
        int movieId = getIntent().getIntExtra("movie_id", -1);

        if (movieId == -1) {
            Toast.makeText(this, "Movie Empty!", Toast.LENGTH_SHORT).show();
        } else {
            // Fetch movie details
            getMovieDetails(movieId);
        }
    }

    private void getMovieDetails(int movieId) {
        Call<MovieDetailsResponse> call = moviesApi.getMovieDetails(movieId);
        call.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieDetailsResponse movieDetails = response.body();
                    displayMovieDetails(movieDetails);
                } else {
                    Toast.makeText(MovieDetailsActivity.this, "Failed to fetch movie details!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
                Toast.makeText(MovieDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayMovieDetails(MovieDetailsResponse movieDetails) {
        titleTextView.setText(movieDetails.getTitle());
        plotTextView.setText(movieDetails.getPlot());
        releasedTextView.setText(movieDetails.getReleased());
        runtimeTextView.setText(movieDetails.getRuntime());
        directorTextView.setText(movieDetails.getDirector());
        writerTextView.setText(movieDetails.getWriter());
        actorsTextView.setText(movieDetails.getActors());
        countryTextView.setText(movieDetails.getCountry());
        imdbRatingTextView.setText(movieDetails.getImdbRating());
        typeTextView.setText(movieDetails.getType());

        // Load poster image using Glide
        Glide.with(this)
                .load(movieDetails.getPoster())
                .into(posterImageView);
    }

    // onClick method for "Back" button
    public void backHome(View view) {
        // Create an Intent to navigate to HomeActivity
        Intent intent = new Intent(MovieDetailsActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();  // Finish the current activity to prevent going back to it
    }

}