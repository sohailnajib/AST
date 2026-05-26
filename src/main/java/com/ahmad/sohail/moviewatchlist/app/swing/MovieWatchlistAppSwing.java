package com.ahmad.sohail.moviewatchlist.app.swing;

import com.ahmad.sohail.moviewatchlist.controller.MovieWatchlistController;
import com.ahmad.sohail.moviewatchlist.repository.mongo.GenreRepositoryMongo;
import com.ahmad.sohail.moviewatchlist.repository.mongo.MovieRepositoryMongo;
import com.ahmad.sohail.moviewatchlist.view.swing.MovieWatchlistViewSwing;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MovieWatchlistAppSwing {

	public static void main(String[] args) {
		String mongoHost = "localhost";
		int mongoPort = 27017;
		String dbName = "moviewatchlist";
		String genreCollection = "genre";
		String movieCollection = "movie";

		for (String arg : args) {
			if (arg.startsWith("--mongo-host="))
				mongoHost = arg.substring("--mongo-host=".length());
			else if (arg.startsWith("--mongo-port="))
				mongoPort = Integer.parseInt(arg.substring("--mongo-port=".length()));
			else if (arg.startsWith("--db-name="))
				dbName = arg.substring("--db-name=".length());
			else if (arg.startsWith("--genre-collection="))
				genreCollection = arg.substring("--genre-collection=".length());
			else if (arg.startsWith("--movie-collection="))
				movieCollection = arg.substring("--movie-collection=".length());
		}

		MongoClient client = new MongoClient(new ServerAddress(mongoHost, mongoPort));
		GenreRepositoryMongo genreRepository = new GenreRepositoryMongo(client, dbName, genreCollection);
		MovieRepositoryMongo movieRepository = new MovieRepositoryMongo(client, dbName, movieCollection);

		javax.swing.SwingUtilities.invokeLater(() -> {
			MovieWatchlistViewSwing view = new MovieWatchlistViewSwing();
			MovieWatchlistController controller = new MovieWatchlistController(view, genreRepository, movieRepository);
			view.setController(controller);
			view.setVisible(true);
			controller.allGenres();
			controller.allMovies();
		});
	}
}