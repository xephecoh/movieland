package com.khamutov.movieland.web.dao;

import com.khamutov.movieland.entity.Movie;
import com.khamutov.movieland.entity.Order;
import com.khamutov.movieland.entity.SortingPattern;

import java.util.List;

public interface MovieDao {
    List<Movie> getAllMovies();

    List<Movie> getRandomMovies(int numberOfMovies);

    List<Movie> getMoviesByGenre(Integer genre);

    List<Movie> getPaginatedListOfMovies(int offset,int limit);

    List<Movie> getAllMoviesSortedByRating(Order order);

    List<Movie> getAllMoviesSortedByDate(SortingPattern sortingPattern);

    void createMovie(String movieName,
                            String description,
                            double price,
                            int year,
                            double rating,
                            List<String> genres);


}

