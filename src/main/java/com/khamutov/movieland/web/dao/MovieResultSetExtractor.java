package com.khamutov.movieland.web.dao;


import com.khamutov.movieland.entity.Genre;
import com.khamutov.movieland.entity.Movie;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class MovieResultSetExtractor implements ResultSetExtractor<List<Movie>> {


    public List<Movie> extractData1(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Movie> movies = new ArrayList<>();
        Map<Integer, Movie> movieMap = new HashMap<>();
        while (resultSet.next()) {
            Movie movie = Movie.builder().movieId(resultSet.getInt("movie_id"))
                    .county(resultSet.getString("county"))
                    .description(resultSet.getString("description"))
                    .movieName(resultSet.getString("movie_name"))
                    .price(resultSet.getDouble("price"))
                    .rating(resultSet.getDouble("rating"))
                    .year(resultSet.getInt("year"))
                    .build();
            movies.add(movie);
        }
        movies.forEach(movie -> populateMap(movieMap, movie));
        return new ArrayList<>(movieMap.values());
    }

    private void populateMap(Map<Integer, Movie> movieMap, Movie movie) {
        int movieId = movie.getMovieId();
        Movie tempMovie = movieMap.get(movieId);
        if (tempMovie != null) {
            if (tempMovie.getGenres() == null) {
                tempMovie.setGenres(new HashSet<>());
            }
            tempMovie.getGenres().add(movie.getGenre());
        } else {
            movieMap.put(movieId, movie);
            if (movie.getGenres() == null) {
                movie.setGenres(new HashSet<>());
            }
            movie.getGenres().add(movie.getGenre());
        }
    }

    @Override
    public List<Movie> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Movie> movies = new ArrayList<>();
        while (resultSet.next()) {
            Movie movie = Movie.builder().movieId(resultSet.getInt("movie_id"))
                    .county(resultSet.getString("county"))
                    .description(resultSet.getString("description"))
                    .movieName(resultSet.getString("movie_name"))
                    .price(resultSet.getDouble("price"))
                    .rating(resultSet.getDouble("rating"))
                    .year(resultSet.getInt("year"))
                    .build();
            Genre genre = Genre.builder()
                    .genreId(resultSet.getInt("genre_id"))
                    .genre(resultSet.getString("genre"))
                    .build();
            Optional<Movie> optionalMovie = movies.stream()
                    .filter(tempMovie -> tempMovie.getMovieId() == movie.getMovieId())
                    .findAny();
            if (optionalMovie.isPresent()) {
                optionalMovie.get().getGenres().add(genre);
            } else {
                if (movie.getGenres() == null) {
                    movie.setGenres(new HashSet<>());
                    movie.getGenres().add(genre);
                }
                movie.getGenres().add(genre);
                movies.add(movie);
            }
        }
        return movies;
    }
}
