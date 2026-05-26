package com.ahmad.sohail.moviewatchlist.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;

import com.ahmad.sohail.moviewatchlist.controller.MovieWatchlistController;
import com.ahmad.sohail.moviewatchlist.model.Genre;
import com.ahmad.sohail.moviewatchlist.model.Movie;
import com.ahmad.sohail.moviewatchlist.repository.mongo.GenreRepositoryMongo;
import com.ahmad.sohail.moviewatchlist.repository.mongo.MovieRepositoryMongo;
import com.ahmad.sohail.moviewatchlist.view.swing.MovieWatchlistViewSwing;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@RunWith(GUITestRunner.class)
public class ModelViewControllerIT extends AssertJSwingJUnitTestCase {

    private static final String DB_NAME = "moviewatchlist";
    private static final String GENRE_COLLECTION = "genre";
    private static final String MOVIE_COLLECTION = "movie";

    @ClassRule
    public static final MongoDBContainer mongo =
        new MongoDBContainer("mongo:4.4.18");

    private MongoClient client;
    private GenreRepositoryMongo genreRepository;
    private MovieRepositoryMongo movieRepository;
    private MovieWatchlistController controller;
    private FrameFixture window;

    @Override
    protected void onSetUp() {
        client = new MongoClient(
            new ServerAddress(mongo.getHost(), mongo.getMappedPort(27017)));
        genreRepository = new GenreRepositoryMongo(client, DB_NAME, GENRE_COLLECTION);
        movieRepository = new MovieRepositoryMongo(client, DB_NAME, MOVIE_COLLECTION);
        for (Genre g : genreRepository.findAll())
            genreRepository.delete(g.getId());
        for (Movie m : movieRepository.findAll())
            movieRepository.delete(m.getId());
        window = new FrameFixture(robot(), GuiActionRunner.execute(() -> {
            MovieWatchlistViewSwing view = new MovieWatchlistViewSwing();
            controller = new MovieWatchlistController(view, genreRepository, movieRepository);
            view.setController(controller);
            return view;
        }));
        window.show();
    }

    @Override
    protected void onTearDown() {
        client.close();
    }

    @Test
    public void testAddGenre() {
        window.textBox("genreIdTextBox").enterText("1");
        window.textBox("genreNameTextBox").enterText("Action");
        window.button(JButtonMatcher.withText("Add Genre")).click();
        assertThat(genreRepository.findById("1"))
            .isEqualTo(new Genre("1", "Action"));
    }

    @Test
    public void testDeleteGenre() {
        genreRepository.save(new Genre("1", "Action"));
        GuiActionRunner.execute(() -> controller.allGenres());
        window.list("genreList").selectItem(0);
        window.button(JButtonMatcher.withText("Delete Genre")).click();
        assertThat(genreRepository.findById("1")).isNull();
    }

    @Test
    public void testAddMovie() {
        window.textBox("movieIdTextBox").enterText("1");
        window.textBox("movieTitleTextBox").enterText("Inception");
        window.textBox("movieYearTextBox").enterText("2010");
        window.button(JButtonMatcher.withText("Add Movie")).click();
        assertThat(movieRepository.findById("1"))
            .isEqualTo(new Movie("1", "Inception", 2010, ""));
    }

    @Test
    public void testDeleteMovie() {
        movieRepository.save(new Movie("1", "Inception", 2010, "1"));
        GuiActionRunner.execute(() -> controller.allMovies());
        window.list("movieList").selectItem(0);
        window.button(JButtonMatcher.withText("Delete Movie")).click();
        assertThat(movieRepository.findById("1")).isNull();
    }
}