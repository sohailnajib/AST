package com.ahmad.sohail.moviewatchlist.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.ahmad.sohail.moviewatchlist.model.Genre;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class GenreRepositoryMongoIT {

	private static final String DB_NAME = "moviewatchlist";
	private static final String COLLECTION_NAME = "genre";

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:6.0.21");

	private MongoClient client;
	private GenreRepositoryMongo genreRepository;
	private MongoCollection<Document> genreCollection;

	@Before
	public void setup() {
		client = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getMappedPort(27017)));
		genreRepository = new GenreRepositoryMongo(client, DB_NAME, COLLECTION_NAME);
		MongoDatabase database = client.getDatabase(DB_NAME);
		database.drop();
		genreCollection = database.getCollection(COLLECTION_NAME);
	}

	@After
	public void tearDown() {
		client.close();
	}

	@Test
	public void testFindAll() {
		genreCollection.insertOne(new Document().append("id", "1").append("name", "Action"));
		genreCollection.insertOne(new Document().append("id", "2").append("name", "Comedy"));
		assertThat(genreRepository.findAll()).containsExactly(new Genre("1", "Action"), new Genre("2", "Comedy"));
	}

	@Test
	public void testFindById() {
		genreCollection.insertOne(new Document().append("id", "1").append("name", "Action"));
		genreCollection.insertOne(new Document().append("id", "2").append("name", "Comedy"));
		assertThat(genreRepository.findById("2")).isEqualTo(new Genre("2", "Comedy"));
	}

	@Test
	public void testSave() {
		genreRepository.save(new Genre("1", "Action"));
		assertThat(genreCollection.find().first().get("name")).isEqualTo("Action");
	}

	@Test
	public void testDelete() {
		genreCollection.insertOne(new Document().append("id", "1").append("name", "Action"));
		genreRepository.delete("1");
		assertThat(genreCollection.countDocuments()).isZero();
	}
}