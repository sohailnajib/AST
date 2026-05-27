package com.ahmad.sohail.moviewatchlist.view.swing;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.ahmad.sohail.moviewatchlist.controller.MovieWatchlistController;
import com.ahmad.sohail.moviewatchlist.model.Genre;
import com.ahmad.sohail.moviewatchlist.model.Movie;

@RunWith(GUITestRunner.class)
public class MovieWatchlistViewSwingTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	private MovieWatchlistViewSwing view;
	private MovieWatchlistController controller;

	@Override
	protected void onSetUp() {
		controller = Mockito.mock(MovieWatchlistController.class);
		view = GuiActionRunner.execute(() -> {
			MovieWatchlistViewSwing v = new MovieWatchlistViewSwing();
			v.setController(controller);
			return v;
		});
		window = new FrameFixture(robot(), view);
		window.show(new java.awt.Dimension(500, 900));
	}

	@Test
	@GUITest
	public void testControlsInitialState() {
		window.label("errorMessageLabel");
		window.list("genreList");
		window.list("movieList");

		window.button("addGenreButton").requireDisabled();
		assertThat(window.button("addGenreButton").target().isEnabled()).isFalse();

		window.button("deleteGenreButton").requireDisabled();
		assertThat(window.button("deleteGenreButton").target().isEnabled()).isFalse();

		window.button("addMovieButton").requireDisabled();
		assertThat(window.button("addMovieButton").target().isEnabled()).isFalse();

		window.button("deleteMovieButton").requireDisabled();
		assertThat(window.button("deleteMovieButton").target().isEnabled()).isFalse();

		window.button("watchedButton").requireDisabled();
		assertThat(window.button("watchedButton").target().isEnabled()).isFalse();

		window.textBox("genreIdTextBox").requireEnabled();
		assertThat(window.textBox("genreIdTextBox").target().isEnabled()).isTrue();

		window.textBox("genreNameTextBox").requireEnabled();
		assertThat(window.textBox("genreNameTextBox").target().isEnabled()).isTrue();

		window.textBox("movieIdTextBox").requireEnabled();
		assertThat(window.textBox("movieIdTextBox").target().isEnabled()).isTrue();

		window.textBox("movieTitleTextBox").requireEnabled();
		assertThat(window.textBox("movieTitleTextBox").target().isEnabled()).isTrue();

		window.textBox("movieYearTextBox").requireEnabled();
		assertThat(window.textBox("movieYearTextBox").target().isEnabled()).isTrue();
	}

	@Test
	@GUITest
	public void testAddGenreButtonShouldBeEnabledWhenGenreIdAndNameAreNotEmpty() {
		window.textBox("genreIdTextBox").enterText("1");
		window.textBox("genreNameTextBox").enterText("Action");

		window.button("addGenreButton").requireEnabled();
		assertThat(window.button("addGenreButton").target().isEnabled()).isTrue();
	}

	@Test
	@GUITest
	public void testAddGenreButtonShouldBeDisabledWhenGenreIdIsEmpty() {
		window.textBox("genreNameTextBox").enterText("Action");

		window.button("addGenreButton").requireDisabled();
		assertThat(window.button("addGenreButton").target().isEnabled()).isFalse();
	}

	@Test
	@GUITest
	public void testAddGenreButtonShouldBeDisabledWhenGenreNameIsEmpty() {
		window.textBox("genreIdTextBox").enterText("1");

		window.button("addGenreButton").requireDisabled();
		assertThat(window.button("addGenreButton").target().isEnabled()).isFalse();
	}

	@Test
	@GUITest
	public void testAddMovieButtonShouldBeEnabledWhenMovieIdTitleAndYearAreNotEmpty() {
		window.textBox("movieIdTextBox").enterText("1");
		window.textBox("movieTitleTextBox").enterText("Inception");
		window.textBox("movieYearTextBox").enterText("2010");

		window.button("addMovieButton").requireEnabled();
		assertThat(window.button("addMovieButton").target().isEnabled()).isTrue();
	}

	@Test
	@GUITest
	public void testAddMovieButtonShouldBeDisabledWhenMovieIdIsEmpty() {
		window.textBox("movieTitleTextBox").enterText("Inception");
		window.textBox("movieYearTextBox").enterText("2010");

		window.button("addMovieButton").requireDisabled();
		assertThat(window.button("addMovieButton").target().isEnabled()).isFalse();
	}

	@Test
	@GUITest
	public void testAddMovieButtonShouldBeDisabledWhenMovieTitleIsEmpty() {
		window.textBox("movieIdTextBox").enterText("1");
		window.textBox("movieYearTextBox").enterText("2010");

		window.button("addMovieButton").requireDisabled();
		assertThat(window.button("addMovieButton").target().isEnabled()).isFalse();
	}

	@Test
	@GUITest
	public void testAddMovieButtonShouldBeDisabledWhenMovieYearIsEmpty() {
		window.textBox("movieIdTextBox").enterText("1");
		window.textBox("movieTitleTextBox").enterText("Inception");

		window.button("addMovieButton").requireDisabled();
		assertThat(window.button("addMovieButton").target().isEnabled()).isFalse();
	}

	@Test
	@GUITest
	public void testDeleteGenreButtonShouldBeEnabledWhenAGenreIsSelected() {
		GuiActionRunner.execute(() -> view.getGenreListModel().addElement(new Genre("1", "Action")));
		window.list("genreList").selectItem(0);

		window.button("deleteGenreButton").requireEnabled();
		assertThat(window.button("deleteGenreButton").target().isEnabled()).isTrue();
	}

	@Test
	@GUITest
	public void testDeleteGenreButtonShouldBeDisabledWhenNoGenreIsSelected() {
		GuiActionRunner.execute(() -> view.getGenreListModel().addElement(new Genre("1", "Action")));
		window.list("genreList").clearSelection();

		window.button("deleteGenreButton").requireDisabled();
		assertThat(window.button("deleteGenreButton").target().isEnabled()).isFalse();
	}

	@Test
	@GUITest
	public void testDeleteMovieButtonShouldBeEnabledWhenAMovieIsSelected() {
		GuiActionRunner.execute(() -> view.getMovieListModel().addElement(new Movie("1", "Inception", 2010, "1")));
		window.list("movieList").selectItem(0);

		window.button("deleteMovieButton").requireEnabled();
		assertThat(window.button("deleteMovieButton").target().isEnabled()).isTrue();
	}

	@Test
	@GUITest
	public void testDeleteMovieButtonShouldBeDisabledWhenNoMovieIsSelected() {
		GuiActionRunner.execute(() -> view.getMovieListModel().addElement(new Movie("1", "Inception", 2010, "1")));
		window.list("movieList").clearSelection();

		window.button("deleteMovieButton").requireDisabled();
		assertThat(window.button("deleteMovieButton").target().isEnabled()).isFalse();
	}

	@Test
	@GUITest
	public void testWatchedButtonShouldBeEnabledWhenAMovieIsSelected() {
		GuiActionRunner.execute(() -> view.getMovieListModel().addElement(new Movie("1", "Inception", 2010, "1")));
		window.list("movieList").selectItem(0);

		window.button("watchedButton").requireEnabled();
		assertThat(window.button("watchedButton").target().isEnabled()).isTrue();
	}

	@Test
	@GUITest
	public void testWatchedButtonShouldBeDisabledWhenNoMovieIsSelected() {
		GuiActionRunner.execute(() -> view.getMovieListModel().addElement(new Movie("1", "Inception", 2010, "1")));
		window.list("movieList").clearSelection();

		window.button("watchedButton").requireDisabled();
		assertThat(window.button("watchedButton").target().isEnabled()).isFalse();
	}

	@Test
	@GUITest
	public void testShowAllGenresShouldAddGenresToList() {
		Genre genre1 = new Genre("1", "Action");
		Genre genre2 = new Genre("2", "Comedy");
		GuiActionRunner.execute(() -> view.showAllGenres(java.util.Arrays.asList(genre1, genre2)));
		assertThat(window.list("genreList").contents()).containsExactly(genre1.toString(), genre2.toString());
	}

	@Test
	@GUITest
	public void testShowAllMoviesShouldAddMoviesToList() {
		Movie movie1 = new Movie("1", "Inception", 2010, "1");
		Movie movie2 = new Movie("2", "Interstellar", 2014, "1");
		GuiActionRunner.execute(() -> view.showAllMovies(java.util.Arrays.asList(movie1, movie2)));
		assertThat(window.list("movieList").contents()).containsExactly(movie1.toString(), movie2.toString());
	}

	@Test
	@GUITest
	public void testGenreAddedShouldAddGenreToList() {
		Genre genre = new Genre("1", "Action");
		GuiActionRunner.execute(() -> view.genreAdded(genre));
		assertThat(window.list("genreList").contents()).containsExactly(genre.toString());
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
		assertThat(window.list("genreList").contents()).containsExactly(genre2.toString());
	}

	@Test
	@GUITest
	public void testMovieAddedShouldAddMovieToList() {
		Movie movie = new Movie("1", "Inception", 2010, "1");
		GuiActionRunner.execute(() -> view.movieAdded(movie));
		assertThat(window.list("movieList").contents()).containsExactly(movie.toString());
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
		assertThat(window.list("movieList").contents()).containsExactly(movie2.toString());
	}

	@Test
	@GUITest
	public void testShowErrorShouldShowMessageInErrorLabel() {
		Genre genre = new Genre("1", "Action");
		GuiActionRunner.execute(() -> view.showError("error message", genre));

		window.label("errorMessageLabel").requireText("error message: " + genre);
		assertThat(window.label("errorMessageLabel").target().getText()).isEqualTo("error message: " + genre);
	}

	@Test
	@GUITest
	public void testGenreAddedShouldClearErrorLabel() {
		GuiActionRunner.execute(() -> {
			view.showError("error", new Genre("1", "Action"));
			view.genreAdded(new Genre("2", "Comedy"));
		});

		window.label("errorMessageLabel").requireText(" ");
		assertThat(window.label("errorMessageLabel").target().getText()).isEqualTo(" ");
	}

	@Test
	@GUITest
	public void testMovieAddedShouldClearErrorLabel() {
		GuiActionRunner.execute(() -> {
			view.showError("error", new Movie("1", "Inception", 2010, "1"));
			view.movieAdded(new Movie("2", "Interstellar", 2014, "1"));
		});

		window.label("errorMessageLabel").requireText(" ");
		assertThat(window.label("errorMessageLabel").target().getText()).isEqualTo(" ");
	}

	@Test
	@GUITest
	public void testAddGenreButtonShouldDelegateToController() {
		window.textBox("genreIdTextBox").enterText("1");
		window.textBox("genreNameTextBox").enterText("Action");
		window.button("addGenreButton").click();
		Mockito.verify(controller).newGenre(new Genre("1", "Action"));
	}

	@Test
	@GUITest
	public void testDeleteGenreButtonShouldDelegateToController() {
		Genre genre = new Genre("1", "Action");
		GuiActionRunner.execute(() -> view.getGenreListModel().addElement(genre));
		window.list("genreList").selectItem(0);
		window.button("deleteGenreButton").click();
		Mockito.verify(controller).deleteGenre(genre);
	}

	@Test
	@GUITest
	public void testAddMovieButtonShouldDelegateToController() {
		window.textBox("movieIdTextBox").enterText("1");
		window.textBox("movieTitleTextBox").enterText("Inception");
		window.textBox("movieYearTextBox").enterText("2010");
		window.button("addMovieButton").click();
		Mockito.verify(controller).newMovie(new Movie("1", "Inception", 2010, "1"));
	}

	@Test
	@GUITest
	public void testDeleteMovieButtonShouldDelegateToController() {
		Movie movie = new Movie("1", "Inception", 2010, "1");
		GuiActionRunner.execute(() -> view.getMovieListModel().addElement(movie));
		window.list("movieList").selectItem(0);
		window.button("deleteMovieButton").click();
		Mockito.verify(controller).deleteMovie(movie);
	}

	@Test
	@GUITest
	public void testWatchedButtonShouldDelegateToController() {
		Movie movie = new Movie("1", "Inception", 2010, "1");
		GuiActionRunner.execute(() -> view.getMovieListModel().addElement(movie));
		window.list("movieList").selectItem(0);
		window.button("watchedButton").click();
		Mockito.verify(controller).markMovieAsWatched(movie);
	}

	@Test
	@GUITest
	public void testMovieUpdatedShouldUpdateMovieInList() {
		Movie movie = new Movie("1", "Inception", 2010, "1");
		GuiActionRunner.execute(() -> view.getMovieListModel().addElement(movie));
		Movie updatedMovie = new Movie("1", "Inception", 2010, "1");
		updatedMovie.setWatched(true);
		GuiActionRunner.execute(() -> view.movieUpdated(updatedMovie));
		assertThat(window.list("movieList").contents()).containsExactly(updatedMovie.toString());
	}

	@Test
	@GUITest
	public void testMovieUpdatedWhenMovieNotInListShouldDoNothing() {
		Movie movie = new Movie("1", "Inception", 2010, "1");
		GuiActionRunner.execute(() -> view.movieUpdated(movie));
		assertThat(window.list("movieList").contents()).isEmpty();
	}

	@Test
	@GUITest
	public void testDeleteGenreButtonShouldNotDelegateWhenNoGenreIsSelected() {
		GuiActionRunner.execute(() -> window.button("deleteGenreButton").target().setEnabled(true));

		window.button("deleteGenreButton").click();

		Mockito.verifyNoInteractions(controller);
	}

	@Test
	@GUITest
	public void testDeleteMovieButtonShouldNotDelegateWhenNoMovieIsSelected() {
		GuiActionRunner.execute(() -> window.button("deleteMovieButton").target().setEnabled(true));

		window.button("deleteMovieButton").click();

		Mockito.verifyNoInteractions(controller);
	}

	@Test
	@GUITest
	public void testWatchedButtonShouldNotDelegateWhenNoMovieIsSelected() {
		GuiActionRunner.execute(() -> window.button("watchedButton").target().setEnabled(true));

		window.button("watchedButton").click();

		Mockito.verifyNoInteractions(controller);
	}

}