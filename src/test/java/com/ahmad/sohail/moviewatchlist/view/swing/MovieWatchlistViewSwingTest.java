package com.ahmad.sohail.moviewatchlist.view.swing;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ahmad.sohail.moviewatchlist.model.Genre;
import com.ahmad.sohail.moviewatchlist.model.Movie;

@RunWith(GUITestRunner.class)
public class MovieWatchlistViewSwingTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	private MovieWatchlistViewSwing view;

	@Override
	protected void onSetUp() {
		view = GuiActionRunner.execute(() -> new MovieWatchlistViewSwing());
		window = new FrameFixture(robot(), view);
		window.show();
	}

	@Test
	@GUITest
	public void testControlsInitialState() {
		window.label("errorMessageLabel");
		window.list("genreList");
		window.list("movieList");
		window.button("addGenreButton").requireDisabled();
		window.button("deleteGenreButton").requireDisabled();
		window.button("addMovieButton").requireDisabled();
		window.button("deleteMovieButton").requireDisabled();
		window.button("watchedButton").requireDisabled();
		window.textBox("genreIdTextBox").requireEnabled();
		window.textBox("genreNameTextBox").requireEnabled();
		window.textBox("movieIdTextBox").requireEnabled();
		window.textBox("movieTitleTextBox").requireEnabled();
		window.textBox("movieYearTextBox").requireEnabled();
	}

	@Test
	@GUITest
	public void testAddGenreButtonShouldBeEnabledWhenGenreIdAndNameAreNotEmpty() {
		window.textBox("genreIdTextBox").enterText("1");
		window.textBox("genreNameTextBox").enterText("Action");
		window.button("addGenreButton").requireEnabled();
	}

	@Test
	@GUITest
	public void testAddGenreButtonShouldBeDisabledWhenGenreIdIsEmpty() {
		window.textBox("genreNameTextBox").enterText("Action");
		window.button("addGenreButton").requireDisabled();
	}

	@Test
	@GUITest
	public void testAddGenreButtonShouldBeDisabledWhenGenreNameIsEmpty() {
		window.textBox("genreIdTextBox").enterText("1");
		window.button("addGenreButton").requireDisabled();
	}

	@Test
	@GUITest
	public void testAddMovieButtonShouldBeEnabledWhenMovieIdTitleAndYearAreNotEmpty() {
		window.textBox("movieIdTextBox").enterText("1");
		window.textBox("movieTitleTextBox").enterText("Inception");
		window.textBox("movieYearTextBox").enterText("2010");
		window.button("addMovieButton").requireEnabled();
	}

	@Test
	@GUITest
	public void testAddMovieButtonShouldBeDisabledWhenMovieIdIsEmpty() {
		window.textBox("movieTitleTextBox").enterText("Inception");
		window.textBox("movieYearTextBox").enterText("2010");
		window.button("addMovieButton").requireDisabled();
	}

	@Test
	@GUITest
	public void testAddMovieButtonShouldBeDisabledWhenMovieTitleIsEmpty() {
		window.textBox("movieIdTextBox").enterText("1");
		window.textBox("movieYearTextBox").enterText("2010");
		window.button("addMovieButton").requireDisabled();
	}

	@Test
	@GUITest
	public void testAddMovieButtonShouldBeDisabledWhenMovieYearIsEmpty() {
		window.textBox("movieIdTextBox").enterText("1");
		window.textBox("movieTitleTextBox").enterText("Inception");
		window.button("addMovieButton").requireDisabled();
	}

	@Test
	@GUITest
	public void testDeleteGenreButtonShouldBeEnabledWhenAGenreIsSelected() {
		GuiActionRunner.execute(() -> view.getGenreListModel().addElement(new Genre("1", "Action")));
		window.list("genreList").selectItem(0);
		window.button("deleteGenreButton").requireEnabled();
	}

	@Test
	@GUITest
	public void testDeleteGenreButtonShouldBeDisabledWhenNoGenreIsSelected() {
		GuiActionRunner.execute(() -> view.getGenreListModel().addElement(new Genre("1", "Action")));
		window.list("genreList").clearSelection();
		window.button("deleteGenreButton").requireDisabled();
	}

	@Test
	@GUITest
	public void testDeleteMovieButtonShouldBeEnabledWhenAMovieIsSelected() {
		GuiActionRunner.execute(() -> view.getMovieListModel().addElement(new Movie("1", "Inception", 2010, "1")));
		window.list("movieList").selectItem(0);
		window.button("deleteMovieButton").requireEnabled();
	}

	@Test
	@GUITest
	public void testDeleteMovieButtonShouldBeDisabledWhenNoMovieIsSelected() {
		GuiActionRunner.execute(() -> view.getMovieListModel().addElement(new Movie("1", "Inception", 2010, "1")));
		window.list("movieList").clearSelection();
		window.button("deleteMovieButton").requireDisabled();
	}

	@Test
	@GUITest
	public void testWatchedButtonShouldBeEnabledWhenAMovieIsSelected() {
		GuiActionRunner.execute(() -> view.getMovieListModel().addElement(new Movie("1", "Inception", 2010, "1")));
		window.list("movieList").selectItem(0);
		window.button("watchedButton").requireEnabled();
	}

	@Test
	@GUITest
	public void testWatchedButtonShouldBeDisabledWhenNoMovieIsSelected() {
		GuiActionRunner.execute(() -> view.getMovieListModel().addElement(new Movie("1", "Inception", 2010, "1")));
		window.list("movieList").clearSelection();
		window.button("watchedButton").requireDisabled();
	}

	@Test
	@GUITest
	public void testShowAllGenresShouldAddGenresToList() {
		Genre genre1 = new Genre("1", "Action");
		Genre genre2 = new Genre("2", "Comedy");
		GuiActionRunner.execute(() -> view.showAllGenres(java.util.Arrays.asList(genre1, genre2)));
		String[] listContents = window.list("genreList").contents();
		assertThat(listContents).containsExactly(genre1.toString(), genre2.toString());
	}

	@Test
	@GUITest
	public void testShowAllMoviesShouldAddMoviesToList() {
		Movie movie1 = new Movie("1", "Inception", 2010, "1");
		Movie movie2 = new Movie("2", "Interstellar", 2014, "1");
		GuiActionRunner.execute(() -> view.showAllMovies(java.util.Arrays.asList(movie1, movie2)));
		String[] listContents = window.list("movieList").contents();
		assertThat(listContents).containsExactly(movie1.toString(), movie2.toString());
	}

	@Test
	@GUITest
	public void testGenreAddedShouldAddGenreToList() {
		Genre genre = new Genre("1", "Action");
		GuiActionRunner.execute(() -> view.genreAdded(genre));
		String[] listContents = window.list("genreList").contents();
		assertThat(listContents).containsExactly(genre.toString());
	}

	@Test
	@GUITest
	public void testGenreRemovedShouldRemoveGenreFromList() {
		Genre genre1 = new Genre("1", "Action");
		Genre genre2 = new Genre("2", "Comedy");
		GuiActionRunner.execute(() -> {
			view.getGenreListModel().addElement(genre1);
			view.getGenreListModel().addElement(genre2);
		});
		GuiActionRunner.execute(() -> view.genreRemoved(genre1));
		String[] listContents = window.list("genreList").contents();
		assertThat(listContents).containsExactly(genre2.toString());
	}

	@Test
	@GUITest
	public void testMovieAddedShouldAddMovieToList() {
		Movie movie = new Movie("1", "Inception", 2010, "1");
		GuiActionRunner.execute(() -> view.movieAdded(movie));
		String[] listContents = window.list("movieList").contents();
		assertThat(listContents).containsExactly(movie.toString());
	}

	@Test
	@GUITest
	public void testMovieRemovedShouldRemoveMovieFromList() {
		Movie movie1 = new Movie("1", "Inception", 2010, "1");
		Movie movie2 = new Movie("2", "Interstellar", 2014, "1");
		GuiActionRunner.execute(() -> {
			view.getMovieListModel().addElement(movie1);
			view.getMovieListModel().addElement(movie2);
		});
		GuiActionRunner.execute(() -> view.movieRemoved(movie1));
		String[] listContents = window.list("movieList").contents();
		assertThat(listContents).containsExactly(movie2.toString());
	}

	@Test
	@GUITest
	public void testShowErrorShouldShowMessageInErrorLabel() {
		Genre genre = new Genre("1", "Action");
		GuiActionRunner.execute(() -> view.showError("error message", genre));
		window.label("errorMessageLabel").requireText("error message: " + genre);
	}

	@Test
	@GUITest
	public void testGenreAddedShouldClearErrorLabel() {
		GuiActionRunner.execute(() -> {
			view.showError("error", new Genre("1", "Action"));
			view.genreAdded(new Genre("2", "Comedy"));
		});
		window.label("errorMessageLabel").requireText(" ");
	}

	@Test
	@GUITest
	public void testMovieAddedShouldClearErrorLabel() {
		GuiActionRunner.execute(() -> {
			view.showError("error", new Movie("1", "Inception", 2010, "1"));
			view.movieAdded(new Movie("2", "Interstellar", 2014, "1"));
		});
		window.label("errorMessageLabel").requireText(" ");
	}

}