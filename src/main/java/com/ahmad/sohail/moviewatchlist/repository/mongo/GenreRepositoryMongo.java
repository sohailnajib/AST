package com.ahmad.sohail.moviewatchlist.repository.mongo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ahmad.sohail.moviewatchlist.model.Genre;
import com.ahmad.sohail.moviewatchlist.repository.GenreRepository;
import com.mongodb.MongoClient;

public class GenreRepositoryMongo implements GenreRepository {

	private MongoClient client;
	private String dbName;
	private String collectionName;

	public GenreRepositoryMongo(MongoClient client, String dbName, String collectionName) {
		this.client = client;
		this.dbName = dbName;
		this.collectionName = collectionName;
	}

	@Override
	public List<Genre> findAll() {
		return StreamSupport
				.stream(client.getDatabase(dbName).getCollection(collectionName).find().spliterator(), false)
				.map(d -> new Genre("" + d.get("id"), "" + d.get("name"))).collect(Collectors.toList());
	}

	@Override
	public Genre findById(String id) {
		return null;
	}

	@Override
	public void save(Genre genre) {
	}

	@Override
	public void delete(String id) {
	}
}