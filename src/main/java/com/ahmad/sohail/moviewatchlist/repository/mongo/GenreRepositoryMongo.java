package com.ahmad.sohail.moviewatchlist.repository.mongo;

import java.util.List;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.ahmad.sohail.moviewatchlist.model.Genre;
import com.ahmad.sohail.moviewatchlist.repository.GenreRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;

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
				.map(d -> new Genre("" + d.get("id"), "" + d.get("name"))).toList();
	}

	@Override
	public Genre findById(String id) {
		Document d = client.getDatabase(dbName).getCollection(collectionName).find(Filters.eq("id", id)).first();
		if (d != null)
			return new Genre("" + d.get("id"), "" + d.get("name"));
		return null;
	}

	@Override
	public void save(Genre genre) {
		client.getDatabase(dbName).getCollection(collectionName)
				.insertOne(new Document().append("id", genre.getId()).append("name", genre.getName()));
	}

	@Override
	public void delete(String id) {
		client.getDatabase(dbName).getCollection(collectionName).deleteOne(Filters.eq("id", id));
	}
}