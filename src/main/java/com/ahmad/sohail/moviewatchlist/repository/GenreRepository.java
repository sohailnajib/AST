package com.ahmad.sohail.moviewatchlist.repository;

import java.util.List;
import com.ahmad.sohail.moviewatchlist.model.Genre;

public interface GenreRepository {
	List<Genre> findAll();

	Genre findById(String id);

	void save(Genre genre);

	void delete(String id);
}