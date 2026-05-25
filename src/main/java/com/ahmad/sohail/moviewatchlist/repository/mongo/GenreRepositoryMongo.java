package com.ahmad.sohail.moviewatchlist.repository.mongo;

import java.util.Collections;
import java.util.List;

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
        return Collections.emptyList();
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