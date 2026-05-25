package com.ahmad.sohail.moviewatchlist.repository;

import java.util.List;
import com.ahmad.sohail.moviewatchlist.model.Movie;

public interface MovieRepository {
	public List<Movie> findAll();

	public Movie findById(String id);

	public void save(Movie movie);

	public void delete(String id);
}