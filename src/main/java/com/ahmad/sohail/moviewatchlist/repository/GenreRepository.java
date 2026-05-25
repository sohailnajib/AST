package com.ahmad.sohail.moviewatchlist.repository;

import java.util.List;
import com.ahmad.sohail.moviewatchlist.model.Genre;

public interface GenreRepository {
	public List<Genre> findAll();

	public Genre findById(String id);

	public void save(Genre genre);

	public void delete(String id);
}