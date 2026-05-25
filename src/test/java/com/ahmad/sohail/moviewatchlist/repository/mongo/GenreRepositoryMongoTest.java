package com.ahmad.sohail.moviewatchlist.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetSocketAddress;

import org.bson.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ahmad.sohail.moviewatchlist.model.Genre;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class GenreRepositoryMongoTest {

    private static final String DB_NAME = "moviewatchlist";
    private static final String COLLECTION_NAME = "genre";

    private static MongoServer server;
    private static InetSocketAddress serverAddress;

    private MongoClient client;
    private GenreRepositoryMongo genreRepository;
    private MongoCollection<Document> genreCollection;

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
    public void testFindAllWhenDatabaseIsEmpty() {
        assertThat(genreRepository.findAll()).isEmpty();
    }
}