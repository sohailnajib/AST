package com.ahmad.sohail.moviewatchlist.controller;

import com.ahmad.sohail.moviewatchlist.model.Genre;
import com.ahmad.sohail.moviewatchlist.model.Movie;
import com.ahmad.sohail.moviewatchlist.repository.GenreRepository;
import com.ahmad.sohail.moviewatchlist.repository.MovieRepository;
import com.ahmad.sohail.moviewatchlist.view.MovieWatchlistView;

public class MovieWatchlistController {

	private MovieWatchlistView view;
	private GenreRepository genreRepository;
	private MovieRepository movieRepository;

	public MovieWatchlistController(MovieWatchlistView view, GenreRepository genreRepository,
			MovieRepository movieRepository) {
		this.view = view;
		this.genreRepository = genreRepository;
		this.movieRepository = movieRepository;
	}

	public void allGenres() {
		view.showAllGenres(genreRepository.findAll());
	}

	public void allMovies() {
		view.showAllMovies(movieRepository.findAll());
	}

	public void newGenre(Genre genre) {
		Genre existing = genreRepository.findById(genre.getId());
		if (existing != null) {
			view.showError("Already existing genre with id " + genre.getId(), existing);
			return;
		}
		genreRepository.save(genre);
		view.genreAdded(genre);
	}

	public void deleteGenre(Genre genre) {
		if (genreRepository.findById(genre.getId()) == null) {
			view.showError("No existing genre with id " + genre.getId(), genre);
			return;
		}
		genreRepository.delete(genre.getId());
		view.genreRemoved(genre);
	}

	public void newMovie(Movie movie) {
		Movie existing = movieRepository.findById(movie.getId());
		if (existing != null) {
			view.showError("Already existing movie with id " + movie.getId(), existing);
			return;
		}
		movieRepository.save(movie);
		view.movieAdded(movie);
	}

	public void deleteMovie(Movie movie) {
		if (movieRepository.findById(movie.getId()) == null) {
			view.showError("No existing movie with id " + movie.getId(), movie);
			return;
		}
		movieRepository.delete(movie.getId());
		view.movieRemoved(movie);
	}

	public void markMovieAsWatched(Movie movie) {
		if (movieRepository.findById(movie.getId()) == null) {
			view.showError("No existing movie with id " + movie.getId(), movie);
			return;
		}
		movie.setWatched(true);
		movieRepository.save(movie);
		view.movieUpdated(movie);
	}
}