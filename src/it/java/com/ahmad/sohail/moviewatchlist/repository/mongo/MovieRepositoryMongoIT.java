package com.ahmad.sohail.moviewatchlist.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.ahmad.sohail.moviewatchlist.model.Movie;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MovieRepositoryMongoIT {

	private static final String DB_NAME = "moviewatchlist";
	private static final String COLLECTION_NAME = "movie";

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.18");

	private MongoClient client;
	private MovieRepositoryMongo movieRepository;
	private MongoCollection<Document> movieCollection;

	@Before
	public void setup() {
		client = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getMappedPort(27017)));
		movieRepository = new MovieRepositoryMongo(client, DB_NAME, COLLECTION_NAME);
		MongoDatabase database = client.getDatabase(DB_NAME);
		database.drop();
		movieCollection = database.getCollection(COLLECTION_NAME);
	}

	@After
	public void tearDown() {
		client.close();
	}

	@Test
	public void testFindAll() {
		movieCollection.insertOne(new Document().append("id", "1").append("title", "Inception").append("year", 2010)
				.append("genreId", "1").append("watched", false));
		movieCollection.insertOne(new Document().append("id", "2").append("title", "Interstellar").append("year", 2014)
				.append("genreId", "1").append("watched", false));
		assertThat(movieRepository.findAll()).containsExactly(new Movie("1", "Inception", 2010, "1"),
				new Movie("2", "Interstellar", 2014, "1"));
	}

	@Test
	public void testFindById() {
		movieCollection.insertOne(new Document().append("id", "1").append("title", "Inception").append("year", 2010)
				.append("genreId", "1").append("watched", false));
		movieCollection.insertOne(new Document().append("id", "2").append("title", "Interstellar").append("year", 2014)
				.append("genreId", "1").append("watched", false));
		assertThat(movieRepository.findById("2")).isEqualTo(new Movie("2", "Interstellar", 2014, "1"));
	}

	@Test
	public void testSave() {
		movieRepository.save(new Movie("1", "Inception", 2010, "1"));

		assertThat(movieCollection.find().first()).containsEntry("title", "Inception");
	}

	@Test
	public void testDelete() {
		movieCollection.insertOne(new Document().append("id", "1").append("title", "Inception").append("year", 2010)
				.append("genreId", "1").append("watched", false));
		movieRepository.delete("1");
		assertThat(movieCollection.countDocuments()).isZero();
	}
}