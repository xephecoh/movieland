package com.khamutov.movieland.repo;

import com.khamutov.movieland.config.GenreResultSetExtractor;
import com.khamutov.movieland.config.MovieResultSetExtractor;
import com.khamutov.movieland.controller.MovieDao;
import com.khamutov.movieland.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
@Slf4j
@RequiredArgsConstructor
public class MovieRepository implements MovieDao {
    private static final String INSERT_NEW_MOVIE_IN_MOVIE_ID = "INSERT INTO movies_genres ()";
    private final JdbcTemplate jdbcTemplate;
    private final MovieResultSetExtractor movieExtractor;
    private final GenreResultSetExtractor genreExtractor;
    private final static String GET_ALL_MOVIES = "select movie.movie_id, movie.county,  movie.description,\n" +
            "movie.movie_name, movie.price, movie.rating, movie.year,genres.genre, genres.genre_id \n" +
            "from movie\n" +
            "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
            "inner join genres on movie_genres.genre_id = genres.genre_id ";

    private final static String GET_RANDOM_MOVIES = "select  movie.movie_id, movie.county,  movie.description, " +
            "movie.movie_name, movie.price, movie.rating, movie.year,genres.genre \n" +
            "from movie \n" +
            "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
            "inner join genres on movie_genres.genre_id = genres.genre_id ORDER BY\n" +
            "RANDOM()" +
            "LIMIT ? ;";
    private final static String GET_MOVIES_GET_GENRE = "select  movie.movie_id, movie.county,  movie.description, " +
            "movie.movie_name, movie.price, movie.rating, movie.year, genres.genre, genres.genre_id \n" +
            "from movie \n" +
            "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
            "inner join genres on movie_genres.genre_id = genres.genre_id\n" +
            "where  genres.genre_id = %s ;";
    private final static String GET_PAGINATED_LIST_OF_MOVIES = "select movie.movie_id, movie.county,  movie.description,\n" +
            "movie.movie_name, movie.price, movie.rating, movie.year, genres.genre, genres.genre_id \n" +
            "from movie\n" +
            "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
            "inner join genres on movie_genres.genre_id = genres.genre_id\n " +
            "offset %s rows \n" +
            "fetch next %S  rows only\n";
    private final static String GET_LIST_OF_MOVIES_SORTED_BY_RATING = "select movie.movie_id, movie.county,  movie.description,\n" +
            "movie.movie_name, movie.price, movie.rating, movie.year,genres.genre, genres.genre_id \n" +
            "from movie\n" +
            "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
            "inner join genres on movie_genres.genre_id = genres.genre_id\n" +
            "order by movie.rating asc ";
    private final static String GET_LIST_OF_MOVIES_SORTED_BY_YEAR = "select movie.movie_id, movie.county,  movie.description,\n" +
            "movie.movie_name, movie.price, movie.rating, movie.year,genres.genre \n" +
            "from movie\n" +
            "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
            "inner join genres on movie_genres.genre_id = genres.genre_id\n " +
            "sorted by year asc ";
    private final static String INSERT_NEW_MOVIE = "INSERT INTO movie (movie_name,description,price,year,rating) \n " +
            "VALUES (?,?,?,?,?);";
    private final static String GET_GENRES_BY_ID = "SELECT * from genres WHERE genre = ?";
    private final static String SET_MOVIE_GENRES_BY_ID = "UPDATE genres_movies SET genres_id = ? WHERE movie_id = ?";
    private final static String INSERT_INTO_MOVIE_GENRES = "INSERT INTO genres_movies (movie_id,genres_id) VALUES (?,?)";


    @Override
    public List<Movie> getAllMovies() {
        return jdbcTemplate.query(GET_ALL_MOVIES, movieExtractor);
    }

    @Override
    public List<Movie> getRandomMovies(int numberOfMovies) {
        return jdbcTemplate.query(GET_RANDOM_MOVIES, movieExtractor, numberOfMovies);
    }

    @Override
    public List<Movie> getMoviesByGenre(Integer genre) {
        return jdbcTemplate.query(String.format(GET_MOVIES_GET_GENRE, genre), movieExtractor);
    }

    @Override
    public List<Movie> getPaginatedListOfMovies(int offset, int limit) {
        String queryWithInjection = String.format(GET_PAGINATED_LIST_OF_MOVIES, offset, limit);
        return jdbcTemplate.query(queryWithInjection, movieExtractor);
    }

    @Override
    public List<Movie> getAllMoviesSortedByRating(Order order) {
        return jdbcTemplate.query(GET_LIST_OF_MOVIES_SORTED_BY_RATING, movieExtractor);
    }

    @Override
    public List<Movie> getAllMoviesSortedByDate(SortingPattern sortingPattern) {
        return jdbcTemplate.query(GET_LIST_OF_MOVIES_SORTED_BY_YEAR, movieExtractor);
    }


    @Transactional
    @Override
    public void createMovie(String movieName,
                            String description,
                            double price,
                            int year,
                            double rating,
                            List<String> genres) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_NEW_MOVIE);
            ps.setString(1, movieName);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setInt(4, year);
            ps.setDouble(5, rating);
            return ps;
        }, keyHolder);

        Integer movieID = (Integer) keyHolder.getKey();
        List<Genre> genresEntity = new ArrayList<>();

        for (String genre : genres) {
            Genre genreEntity = jdbcTemplate.query(GET_GENRES_BY_ID, genreExtractor, genre);
            genresEntity.add(genreEntity);
        }

        List<MovieGenre> movieGenres = genresEntity.stream()
                .map(genre -> new MovieGenre(genre.getGenreId(), movieID))
                .collect(Collectors.toList());

        movieGenres.forEach(movieGenre -> {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(INSERT_NEW_MOVIE_IN_MOVIE_ID);
                ps.setString(1, movieName);
                ps.setString(2, description);
                return ps;
            }, keyHolder);
        });
    }
}
