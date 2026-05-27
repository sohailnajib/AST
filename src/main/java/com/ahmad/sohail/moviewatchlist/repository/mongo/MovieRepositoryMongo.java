package com.ahmad.sohail.moviewatchlist.repository.mongo;

import java.util.List;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.ahmad.sohail.moviewatchlist.model.Movie;
import com.ahmad.sohail.moviewatchlist.repository.MovieRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;

public class MovieRepositoryMongo implements MovieRepository {

	private static final String GENRE_ID = "genreId";
	private static final String TITLE = "title";
	private MongoClient client;
	private String dbName;
	private String collectionName;

	public MovieRepositoryMongo(MongoClient client, String dbName, String collectionName) {
		this.client = client;
		this.dbName = dbName;
		this.collectionName = collectionName;
	}

	@Override
	public List<Movie> findAll() {
		return StreamSupport
				.stream(client.getDatabase(dbName).getCollection(collectionName).find().spliterator(), false)
				.map(d -> new Movie("" + d.get("id"), "" + d.get(TITLE), (int) d.get("year"), "" + d.get(GENRE_ID)))
				.toList();
	}

	@Override
	public Movie findById(String id) {
		Document d = client.getDatabase(dbName).getCollection(collectionName).find(Filters.eq("id", id)).first();
		if (d != null)
			return new Movie("" + d.get("id"), "" + d.get(TITLE), (int) d.get("year"), "" + d.get(GENRE_ID));
		return null;
	}

	@Override
	public void save(Movie movie) {
		client.getDatabase(dbName).getCollection(collectionName)
				.insertOne(new Document().append("id", movie.getId()).append(TITLE, movie.getTitle())
						.append("year", movie.getYear()).append(GENRE_ID, movie.getGenreId())
						.append("watched", movie.isWatched()));
	}

	@Override
	public void delete(String id) {
		client.getDatabase(dbName).getCollection(collectionName).deleteOne(Filters.eq("id", id));
	}
}