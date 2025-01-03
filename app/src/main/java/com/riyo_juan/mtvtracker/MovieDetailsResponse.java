package com.riyo_juan.mtvtracker;

import com.google.gson.annotations.SerializedName;

public class MovieDetailsResponse {
    @SerializedName("title")
    private String title;

    @SerializedName("poster")
    private String poster;

    @SerializedName("plot")
    private String plot;

    @SerializedName("released")
    private String released;

    @SerializedName("runtime")
    private String runtime;

    @SerializedName("director")
    private String director;

    @SerializedName("writer")
    private String writer;

    @SerializedName("actors")
    private String actors;

    @SerializedName("country")
    private String country;

    @SerializedName("imdb_rating")
    private String imdbRating;

    @SerializedName("type")
    private String type;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getPlot() {
        return plot;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActors() {
        return actors;
    }

    public String getCountry() {
        return country;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getType() {
        return type;
    }
}
