package com.ahmad.sohail.moviewatchlist.view.swing;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

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
}