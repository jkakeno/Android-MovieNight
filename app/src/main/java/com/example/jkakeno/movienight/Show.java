package com.example.jkakeno.movienight;

public class Show {
    private Movie[] mMovies;
    private TVShow[] mTVShows;

    public TVShow[] getTVShows() {
        return mTVShows;
    }

    public void setTVShows(TVShow[] TVShows) {
        mTVShows = TVShows;
    }

    public Movie[] getMovies() {
        return mMovies;
    }

    public void setMovies(Movie[] movies) {
        mMovies = movies;
    }

}
