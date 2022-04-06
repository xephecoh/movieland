package com.khamutov.movieland.services;

import com.khamutov.movieland.entity.Movie;

import java.util.List;

public interface MovieService {

    public List<Movie> getAllMoviesSortedByPrice(String currency);

    public List<Movie> getAllMoviesSortedByPricePaginated(Integer limit, Integer offset, String currency);

    public List<Movie> getAllMoviesSortedByRating(String rating,String currency);

    public List<Movie> getAllMoviesSortedByYear(String date,String currency);

    public List<Movie> getRandomMovies(int randomNumber,String currency);

    public List<Movie> getMoviesByGenre(Integer genre,String currency);


}
