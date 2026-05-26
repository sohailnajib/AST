package com.ahmad.sohail.moviewatchlist.view.swing;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.ahmad.sohail.moviewatchlist.controller.MovieWatchlistController;
import com.ahmad.sohail.moviewatchlist.model.Genre;
import com.ahmad.sohail.moviewatchlist.model.Movie;
import com.ahmad.sohail.moviewatchlist.view.MovieWatchlistView;

public class MovieWatchlistViewSwing extends JFrame implements MovieWatchlistView {

	private static final long serialVersionUID = 1L;

	private JTextField genreIdTextBox;
	private JTextField genreNameTextBox;
	private JTextField movieIdTextBox;
	private JTextField movieTitleTextBox;
	private JTextField movieYearTextBox;

	private JButton addGenreButton;
	private JButton deleteGenreButton;
	private JButton addMovieButton;
	private JButton deleteMovieButton;
	private JButton watchedButton;

	private JList<Genre> genreList;
	private DefaultListModel<Genre> genreListModel;

	private JList<Movie> movieList;
	private DefaultListModel<Movie> movieListModel;

	private JLabel errorMessageLabel;

	private MovieWatchlistController controller;

	public MovieWatchlistController getController() {
		return controller;
	}

	public void setController(MovieWatchlistController controller) {
		this.controller = controller;
	}

	public MovieWatchlistViewSwing() {
		setTitle("Movie Watchlist");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Genre text fields
		genreIdTextBox = new JTextField();
		genreIdTextBox.setName("genreIdTextBox");

		genreNameTextBox = new JTextField();
		genreNameTextBox.setName("genreNameTextBox");

		// Movie text fields
		movieIdTextBox = new JTextField();
		movieIdTextBox.setName("movieIdTextBox");

		movieTitleTextBox = new JTextField();
		movieTitleTextBox.setName("movieTitleTextBox");

		movieYearTextBox = new JTextField();
		movieYearTextBox.setName("movieYearTextBox");

		// Genre buttons
		addGenreButton = new JButton("Add Genre");
		addGenreButton.setName("addGenreButton");
		addGenreButton.setEnabled(false);

		deleteGenreButton = new JButton("Delete Genre");
		deleteGenreButton.setName("deleteGenreButton");
		deleteGenreButton.setEnabled(false);

		// Movie buttons
		addMovieButton = new JButton("Add Movie");
		addMovieButton.setName("addMovieButton");
		addMovieButton.setEnabled(false);

		deleteMovieButton = new JButton("Delete Movie");
		deleteMovieButton.setName("deleteMovieButton");
		deleteMovieButton.setEnabled(false);

		watchedButton = new JButton("Mark as Watched");
		watchedButton.setName("watchedButton");
		watchedButton.setEnabled(false);

		// Lists
		genreListModel = new DefaultListModel<>();
		genreList = new JList<>(genreListModel);
		genreList.setName("genreList");
		genreList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		movieListModel = new DefaultListModel<>();
		movieList = new JList<>(movieListModel);
		movieList.setName("movieList");
		movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Error label
		errorMessageLabel = new JLabel(" ");
		errorMessageLabel.setName("errorMessageLabel");

		// Add all components to frame
		getContentPane().setLayout(new java.awt.GridLayout(0, 1));
		getContentPane().add(new JLabel("Genre ID:"));
		getContentPane().add(genreIdTextBox);
		getContentPane().add(new JLabel("Genre Name:"));
		getContentPane().add(genreNameTextBox);
		getContentPane().add(addGenreButton);
		getContentPane().add(deleteGenreButton);
		getContentPane().add(genreList);
		getContentPane().add(new JLabel("Movie ID:"));
		getContentPane().add(movieIdTextBox);
		getContentPane().add(new JLabel("Movie Title:"));
		getContentPane().add(movieTitleTextBox);
		getContentPane().add(new JLabel("Movie Year:"));
		getContentPane().add(movieYearTextBox);
		getContentPane().add(addMovieButton);
		getContentPane().add(deleteMovieButton);
		getContentPane().add(watchedButton);
		getContentPane().add(movieList);
		getContentPane().add(errorMessageLabel);
		addDocumentListenerToEnableAddGenreButton();
		addDocumentListenerToEnableAddMovieButton();

		pack();
	}

	private void addDocumentListenerToEnableAddGenreButton() {
		javax.swing.event.DocumentListener listener = new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				updateAddGenreButton();
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				updateAddGenreButton();
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				updateAddGenreButton();
			}

			private void updateAddGenreButton() {
				addGenreButton.setEnabled(
						!genreIdTextBox.getText().trim().isEmpty() && !genreNameTextBox.getText().trim().isEmpty());
			}
		};
		genreIdTextBox.getDocument().addDocumentListener(listener);
		genreNameTextBox.getDocument().addDocumentListener(listener);
	}

	private void addDocumentListenerToEnableAddMovieButton() {
		javax.swing.event.DocumentListener listener = new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				updateAddMovieButton();
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				updateAddMovieButton();
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				updateAddMovieButton();
			}

			private void updateAddMovieButton() {
				addMovieButton.setEnabled(
						!movieIdTextBox.getText().trim().isEmpty() && !movieTitleTextBox.getText().trim().isEmpty()
								&& !movieYearTextBox.getText().trim().isEmpty());
			}
		};
		movieIdTextBox.getDocument().addDocumentListener(listener);
		movieTitleTextBox.getDocument().addDocumentListener(listener);
		movieYearTextBox.getDocument().addDocumentListener(listener);
	}

	@Override
	public void showAllGenres(List<Genre> genres) {
		genres.stream().forEach(genreListModel::addElement);
	}

	@Override
	public void showAllMovies(List<Movie> movies) {
		movies.stream().forEach(movieListModel::addElement);
	}

	@Override
	public void showError(String message, Object entity) {
		errorMessageLabel.setText(message + ": " + entity);
	}

	@Override
	public void genreAdded(Genre genre) {
		genreListModel.addElement(genre);
		errorMessageLabel.setText(" ");
	}

	@Override
	public void genreRemoved(Genre genre) {
		genreListModel.removeElement(genre);
		errorMessageLabel.setText(" ");
	}

	@Override
	public void movieAdded(Movie movie) {
		movieListModel.addElement(movie);
		errorMessageLabel.setText(" ");
	}

	@Override
	public void movieRemoved(Movie movie) {
		movieListModel.removeElement(movie);
		errorMessageLabel.setText(" ");
	}

	@Override
	public void movieUpdated(Movie movie) {
		int index = movieListModel.indexOf(movie);
		if (index != -1) {
			movieListModel.set(index, movie);
		}
		errorMessageLabel.setText(" ");
	}

	DefaultListModel<Genre> getGenreListModel() {
		return genreListModel;
	}

	DefaultListModel<Movie> getMovieListModel() {
		return movieListModel;
	}
}