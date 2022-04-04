package com.khamutov.movieland.config;


import com.khamutov.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
      return new Movie(
              resultSet.getInt("movie_id"),
              resultSet.getString("county"),
              resultSet.getString("description"),
              resultSet.getString("movie_name"),
              resultSet.getDouble("price"),
              resultSet.getDouble("rating"),
              resultSet.getInt("year"),
              resultSet.getString("genre")
      );
    }

    public void secondOption(ResultSet resultSet) throws SQLException {
        int movieId;
        Map<Integer, Movie> movieMap = new HashMap<>();
        Movie movie = new Movie();
        while (resultSet.next()) {
            movieId = resultSet.getInt("movie_id");
            if (movieMap.get(movieId) != null) {
                movie.getGenres().add(resultSet.getString("genre"));
            }else {
                movie.setMovieName(resultSet.getString("movie_name"));
                movie.setYear(resultSet.getInt("year"));
                movie.setCounty(resultSet.getString("county"));
                movie.setDescription(resultSet.getString("description"));
                movie.setRating(resultSet.getDouble("rating"));
                movie.setPrice(resultSet.getDouble("price"));
                movieMap.put(movieId, movie);
            }
        }
    }
}
