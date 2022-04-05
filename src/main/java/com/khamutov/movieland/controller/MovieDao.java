package com.khamutov.movieland.controller;

import com.khamutov.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {
    List<Movie> getAllMovies();

    List<Movie> getRandomMovies(int numberOfMovies);

    List<Movie> getMoviesByGenre(String genre);

    List<Movie> getPaginatedListOfMovies(int offset,int limit);

    List<Movie> getAllMoviesSortedByRating(String rating);
}

