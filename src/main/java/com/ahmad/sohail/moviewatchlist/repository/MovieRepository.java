package com.ahmad.sohail.moviewatchlist.repository;

import java.util.List;
import com.ahmad.sohail.moviewatchlist.model.Movie;

public interface MovieRepository {
	List<Movie> findAll();

	Movie findById(String id);

	void save(Movie movie);

	void delete(String id);
}