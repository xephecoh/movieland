package com.khamutov.movieland.repo;

import com.khamutov.movieland.config.MovieRowMapper;
import com.khamutov.movieland.controller.MovieDao;
import com.khamutov.movieland.entity.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MovieRepository implements MovieDao {
    private final JdbcTemplate jdbcTemplate;

    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> getAllMovies() {
        String query = "select movie.movie_id, movie.county,  movie.description, " +
                "movie.movie_name, movie.price, movie.rating, movie.year,genres.genre \n" +
                "from movie \n" +
                "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
                "inner join genres on movie_genres.genre_id = genres.genre_id ";
        return jdbcTemplate.query(query, new MovieRowMapper());
    }

    @Override
    public List<Movie> getRandomMovies(int numberOfMovies) {
        String query = "select  movie.movie_id, movie.county,  movie.description, " +
                "movie.movie_name, movie.price, movie.rating, movie.year,genres.genre \n" +
                "from movie \n" +
                "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
                "inner join genres on movie_genres.genre_id = genres.genre_id ORDER BY\n" +
                "RANDOM()" +
                "LIMIT " + numberOfMovies + " ;";
        return jdbcTemplate.query(query, new MovieRowMapper());
    }

    @Override
    public List<Movie> getMoviesByGenre(String genre) {
        String query = "select  movie.movie_id, movie.county,  movie.description, " +
                "movie.movie_name, movie.price, movie.rating, movie.year, genres.genre \n" +
                "from movie \n" +
                "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
                "inner join genres on movie_genres.genre_id = genres.genre_id\n" +
                "where  genres.genre = "+ "'" + genre + "'" + " ;";
        return jdbcTemplate.query(query, new MovieRowMapper());
    }


}
