package com.khamutov.movieland.config;


import com.khamutov.movieland.entity.Movie;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class MovieResultSetExtractor implements ResultSetExtractor<List<Movie>> {

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
                    .genre(resultSet.getString("genre"))
                    .build();
            movies.add(movie);
        }
        Map<Integer, Movie> movieMap = new HashMap<>();
        movies.forEach(movie -> populateMap(movieMap, movie));
        return new ArrayList<>(movieMap.values());
    }

    private void populateMap(Map<Integer, Movie> movieMap, Movie movie) {
        int movieId = movie.getMovieId();
        Movie tempMovie = movieMap.get(movieId);
        if (tempMovie != null) {
            tempMovie.getGenres().add(movie.getGenre());
        } else {
            movieMap.put(movieId, movie);
            movie.getGenres().add(movie.getGenre());
        }
    }
}
