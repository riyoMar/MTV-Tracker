package com.riyo_juan.mtvtracker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface MoviesApi {
    @GET("movies")
    Call<MovieResponse> getMovies(@Query("page") int page);

    @GET("movies/{movie_id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("movie_id") int movieId);
}
