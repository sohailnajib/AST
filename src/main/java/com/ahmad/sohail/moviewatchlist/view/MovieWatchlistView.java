package com.ahmad.sohail.moviewatchlist.view;

import java.util.List;

import com.ahmad.sohail.moviewatchlist.model.Genre;
import com.ahmad.sohail.moviewatchlist.model.Movie;

public interface MovieWatchlistView {
    void showAllGenres(List<Genre> genres);
    void showAllMovies(List<Movie> movies);
    void showError(String message, Object entity);
    void genreAdded(Genre genre);
    void genreRemoved(Genre genre);
    void movieAdded(Movie movie);
    void movieRemoved(Movie movie);
    void movieUpdated(Movie movie);
}