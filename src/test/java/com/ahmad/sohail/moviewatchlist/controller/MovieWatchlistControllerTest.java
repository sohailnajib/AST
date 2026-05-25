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
}