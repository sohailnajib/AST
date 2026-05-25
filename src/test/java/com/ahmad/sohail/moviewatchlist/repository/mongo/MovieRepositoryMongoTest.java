package com.ahmad.sohail.moviewatchlist.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ahmad.sohail.moviewatchlist.model.Movie;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class MovieRepositoryMongoTest {

    private static final String DB_NAME = "moviewatchlist";
    private static final String COLLECTION_NAME = "movie";

    private static MongoServer server;
    private static InetSocketAddress serverAddress;

    private MongoClient client;
    private MovieRepositoryMongo movieRepository;
    private MongoCollection<Document> movieCollection;

    @BeforeClass
    public static void setupServer() {
        server = new MongoServer(new MemoryBackend());
        serverAddress = server.bind();
    }

    @AfterClass
    public static void shutdownServer() {
        server.shutdown();
    }

    @Before
    public void setup() {
        client = new MongoClient(new ServerAddress(serverAddress));
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
    public void testFindAllWhenDatabaseIsEmpty() {
        assertThat(movieRepository.findAll()).isEmpty();
    }

    @Test
    public void testFindAllWhenDatabaseIsNotEmpty() {
        addTestMovieToDatabase("1", "Inception", 2010, "1", false);
        addTestMovieToDatabase("2", "The Matrix", 1999, "1", false);
        assertThat(movieRepository.findAll())
            .containsExactly(
                new Movie("1", "Inception", 2010, "1"),
                new Movie("2", "The Matrix", 1999, "1"));
    }

    @Test
    public void testFindByIdNotFound() {
        assertThat(movieRepository.findById("1")).isNull();
    }

    @Test
    public void testFindByIdFound() {
        addTestMovieToDatabase("1", "Inception", 2010, "1", false);
        addTestMovieToDatabase("2", "The Matrix", 1999, "1", false);
        assertThat(movieRepository.findById("2"))
            .isEqualTo(new Movie("2", "The Matrix", 1999, "1"));
    }

    @Test
    public void testSave() {
        Movie movie = new Movie("1", "Inception", 2010, "1");
        movieRepository.save(movie);
        assertThat(readAllMoviesFromDatabase())
            .containsExactly(new Movie("1", "Inception", 2010, "1"));
    }

    @Test
    public void testDelete() {
        addTestMovieToDatabase("1", "Inception", 2010, "1", false);
        movieRepository.delete("1");
        assertThat(readAllMoviesFromDatabase()).isEmpty();
    }

    private void addTestMovieToDatabase(String id, String title, 
            int year, String genreId, boolean watched) {
        movieCollection.insertOne(new Document()
            .append("id", id)
            .append("title", title)
            .append("year", year)
            .append("genreId", genreId)
            .append("watched", watched));
    }

    private List<Movie> readAllMoviesFromDatabase() {
        return StreamSupport
            .stream(movieCollection.find().spliterator(), false)
            .map(d -> new Movie(
                "" + d.get("id"),
                "" + d.get("title"),
                (int) d.get("year"),
                "" + d.get("genreId")))
            .collect(Collectors.toList());
    }
}