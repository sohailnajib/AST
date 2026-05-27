package com.ahmad.sohail.moviewatchlist.view.swing;

import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.core.api.Assertions.assertThat;

import javax.swing.JFrame;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;

import com.ahmad.sohail.moviewatchlist.app.swing.MovieWatchlistAppSwing;

@RunWith(GUITestRunner.class)
public class MovieWatchlistAppSwingE2ETest extends AssertJSwingJUnitTestCase {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.18");

	private FrameFixture window;

	@Override
	protected void onSetUp() {
		application(MovieWatchlistAppSwing.class)
				.withArgs("--mongo-host=" + mongo.getHost(), "--mongo-port=" + mongo.getMappedPort(27017),
						"--db-name=moviewatchlist", "--genre-collection=genre", "--movie-collection=movie")
				.start();
		window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			@Override
			protected boolean isMatching(JFrame frame) {
				return "Movie Watchlist".equals(frame.getTitle()) && frame.isShowing();
			}
		}).using(robot());
	}

	@Test
	@GUITest
	public void testAddGenreAndMovie() {
		window.textBox("genreIdTextBox").enterText("1");
		window.textBox("genreNameTextBox").enterText("Action");
		window.button(JButtonMatcher.withText("Add Genre")).click();
		window.list("genreList").requireItemCount(1);
		assertThat(window.list("genreList").contents()).hasSize(1);

		window.textBox("movieIdTextBox").enterText("1");
		window.textBox("movieTitleTextBox").enterText("Inception");
		window.textBox("movieYearTextBox").enterText("2010");
		window.button(JButtonMatcher.withText("Add Movie")).click();
		window.list("movieList").requireItemCount(1);
		assertThat(window.list("movieList").contents()).hasSize(1);
	}

	@Test
	@GUITest
	public void testDeleteGenre() {
		window.textBox("genreIdTextBox").enterText("1");
		window.textBox("genreNameTextBox").enterText("Action");
		window.button(JButtonMatcher.withText("Add Genre")).click();
		window.list("genreList").selectItem(0);
		window.button(JButtonMatcher.withText("Delete Genre")).click();
		window.list("genreList").requireItemCount(0);
		assertThat(window.list("genreList").contents()).isEmpty();
	}

	@Test
	@GUITest
	public void testDeleteMovie() {
		window.textBox("movieIdTextBox").enterText("1");
		window.textBox("movieTitleTextBox").enterText("Inception");
		window.textBox("movieYearTextBox").enterText("2010");
		window.button(JButtonMatcher.withText("Add Movie")).click();
		window.list("movieList").selectItem(0);
		window.button(JButtonMatcher.withText("Delete Movie")).click();
		window.list("movieList").requireItemCount(0);
		assertThat(window.list("movieList").contents()).isEmpty();
	}

	@Test
	@GUITest
	public void testMarkMovieAsWatched() {
		window.textBox("movieIdTextBox").enterText("1");
		window.textBox("movieTitleTextBox").enterText("Inception");
		window.textBox("movieYearTextBox").enterText("2010");
		window.button(JButtonMatcher.withText("Add Movie")).click();
		window.list("movieList").selectItem(0);
		window.button(JButtonMatcher.withText("Mark as Watched")).click();
		window.list("movieList").requireItemCount(1);
		assertThat(window.list("movieList").contents()).hasSize(1);
	}
}