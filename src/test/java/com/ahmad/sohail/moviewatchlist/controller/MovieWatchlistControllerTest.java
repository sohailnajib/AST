package com.ahmad.sohail.moviewatchlist.controller;

import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ahmad.sohail.moviewatchlist.model.Genre;
import com.ahmad.sohail.moviewatchlist.model.Movie;
import com.ahmad.sohail.moviewatchlist.repository.GenreRepository;
import com.ahmad.sohail.moviewatchlist.repository.MovieRepository;
import com.ahmad.sohail.moviewatchlist.view.MovieWatchlistView;

public class MovieWatchlistControllerTest {

	@Mock
	private MovieWatchlistView view;

	@Mock
	private GenreRepository genreRepository;

	@Mock
	private MovieRepository movieRepository;

	private MovieWatchlistController controller;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		controller = new MovieWatchlistController(view, genreRepository, movieRepository);
	}

	@Test
	public void testAllGenres() {
		java.util.List<Genre> genres = Arrays.asList(new Genre("1", "Action"));
		when(genreRepository.findAll()).thenReturn(genres);
		controller.allGenres();
		verify(view).showAllGenres(genres);
	}

	@Test
	public void testAllMovies() {
		java.util.List<Movie> movies = Arrays.asList(new Movie("1", "Inception", 2010, "1"));
		when(movieRepository.findAll()).thenReturn(movies);
		controller.allMovies();
		verify(view).showAllMovies(movies);
	}

	@Test
	public void testNewGenreWhenGenreDoesNotExist() {
		when(genreRepository.findById("1")).thenReturn(null);
		Genre genre = new Genre("1", "Action");
		controller.newGenre(genre);
		InOrder inOrder = inOrder(genreRepository, view);
		inOrder.verify(genreRepository).save(genre);
		inOrder.verify(view).genreAdded(genre);
	}

	@Test
	public void testNewGenreWhenGenreAlreadyExists() {
		Genre existingGenre = new Genre("1", "Action");
		when(genreRepository.findById("1")).thenReturn(existingGenre);
		controller.newGenre(new Genre("1", "Action"));
		verify(view).showError("Already existing genre with id 1", existingGenre);
		verifyNoMoreInteractions(ignoreStubs(genreRepository));
	}

	@Test
	public void testDeleteGenreWhenGenreExists() {
		Genre genreToDelete = new Genre("1", "Action");
		when(genreRepository.findById("1")).thenReturn(genreToDelete);
		controller.deleteGenre(genreToDelete);
		InOrder inOrder = inOrder(genreRepository, view);
		inOrder.verify(genreRepository).delete("1");
		inOrder.verify(view).genreRemoved(genreToDelete);
	}

	@Test
	public void testDeleteGenreWhenGenreDoesNotExist() {
		Genre genre = new Genre("1", "Action");
		when(genreRepository.findById("1")).thenReturn(null);
		controller.deleteGenre(genre);
		verify(view).showError("No existing genre with id 1", genre);
		verifyNoMoreInteractions(ignoreStubs(genreRepository));
	}

	@Test
	public void testNewMovieWhenMovieDoesNotExist() {
		when(movieRepository.findById("1")).thenReturn(null);
		Movie movie = new Movie("1", "Inception", 2010, "1");
		controller.newMovie(movie);
		InOrder inOrder = inOrder(movieRepository, view);
		inOrder.verify(movieRepository).save(movie);
		inOrder.verify(view).movieAdded(movie);
	}

	@Test
	public void testNewMovieWhenMovieAlreadyExists() {
		Movie existingMovie = new Movie("1", "Inception", 2010, "1");
		when(movieRepository.findById("1")).thenReturn(existingMovie);
		controller.newMovie(new Movie("1", "Inception", 2010, "1"));
		verify(view).showError("Already existing movie with id 1", existingMovie);
		verifyNoMoreInteractions(ignoreStubs(movieRepository));
	}

	@Test
	public void testDeleteMovieWhenMovieExists() {
		Movie movieToDelete = new Movie("1", "Inception", 2010, "1");
		when(movieRepository.findById("1")).thenReturn(movieToDelete);
		controller.deleteMovie(movieToDelete);
		InOrder inOrder = inOrder(movieRepository, view);
		inOrder.verify(movieRepository).delete("1");
		inOrder.verify(view).movieRemoved(movieToDelete);
	}

	@Test
	public void testDeleteMovieWhenMovieDoesNotExist() {
		Movie movie = new Movie("1", "Inception", 2010, "1");
		when(movieRepository.findById("1")).thenReturn(null);
		controller.deleteMovie(movie);
		verify(view).showError("No existing movie with id 1", movie);
		verifyNoMoreInteractions(ignoreStubs(movieRepository));
	}

	@Test
	public void testMarkMovieAsWatchedWhenMovieExists() {
		Movie movie = new Movie("1", "Inception", 2010, "1");
		when(movieRepository.findById("1")).thenReturn(movie);
		controller.markMovieAsWatched(movie);
		InOrder inOrder = inOrder(movieRepository, view);
		inOrder.verify(movieRepository).save(movie);
		inOrder.verify(view).movieUpdated(movie);
	}

	@Test
	public void testMarkMovieAsWatchedWhenMovieDoesNotExist() {
		Movie movie = new Movie("1", "Inception", 2010, "1");
		when(movieRepository.findById("1")).thenReturn(null);
		controller.markMovieAsWatched(movie);
		verify(view).showError("No existing movie with id 1", movie);
		verifyNoMoreInteractions(ignoreStubs(movieRepository));
	}
}