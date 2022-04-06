package com.khamutov.movieland.repo;

import com.khamutov.movieland.config.MovieResultSetExtractor;
import com.khamutov.movieland.controller.MovieDao;
import com.khamutov.movieland.entity.Movie;
import com.khamutov.movieland.entity.SortingPattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
@RequiredArgsConstructor
public class MovieRepository implements MovieDao {
    private final JdbcTemplate jdbcTemplate;
    private final MovieResultSetExtractor extractor;
    private final static String GET_ALL_MOVIES = "select movie.movie_id, movie.county,  movie.description,\n" +
            "movie.movie_name, movie.price, movie.rating, movie.year,genres.genre \n" +
            "from movie\n" +
            "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
            "inner join genres on movie_genres.genre_id = genres.genre_id ";

    private final static String  GET_RANDOM_MOVIES = "select  movie.movie_id, movie.county,  movie.description, " +
            "movie.movie_name, movie.price, movie.rating, movie.year,genres.genre \n" +
            "from movie \n" +
            "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
            "inner join genres on movie_genres.genre_id = genres.genre_id ORDER BY\n" +
            "RANDOM()" +
            "LIMIT %s ;";
    private final static String GET_MOVIES_GET_GENRE = "select  movie.movie_id, movie.county,  movie.description, " +
            "movie.movie_name, movie.price, movie.rating, movie.year, genres.genre, genres.genre_id \n" +
            "from movie \n" +
            "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
            "inner join genres on movie_genres.genre_id = genres.genre_id\n" +
            "where  genres.genre_id = %s ;";
    private final static String GET_PAGINATED_LIST_OF_MOVIES = "select movie.movie_id, movie.county,  movie.description,\n" +
            "movie.movie_name, movie.price, movie.rating, movie.year,genres.genre \n" +
            "from movie\n" +
            "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
            "inner join genres on movie_genres.genre_id = genres.genre_id\n " +
            "offset %s rows \n" +
            "fetch next %S  rows only\n";
    private final static String GET_LIST_OF_MOVIES_SORTED_BY_RATING = "select movie.movie_id, movie.county,  movie.description,\n" +
            "movie.movie_name, movie.price, movie.rating, movie.year,genres.genre \n" +
            "from movie\n" +
            "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
            "inner join genres on movie_genres.genre_id = genres.genre_id\n " +
            "sorted by rating %s";
    private final static String GET_LIST_OF_MOVIES_SORTED_BY_YEAR = "select movie.movie_id, movie.county,  movie.description,\n" +
            "movie.movie_name, movie.price, movie.rating, movie.year,genres.genre \n" +
            "from movie\n" +
            "inner  join  movie_genres on movie.movie_id = movie_genres.movie_id\n" +
            "inner join genres on movie_genres.genre_id = genres.genre_id\n " +
            "sorted by year %s";




    @Override
    public List<Movie> getAllMovies() {
        return jdbcTemplate.query(GET_ALL_MOVIES, extractor);
    }

    @Override
    public List<Movie> getRandomMovies(int numberOfMovies) {
        String queryWithInjection = String.format(GET_RANDOM_MOVIES, numberOfMovies);
        return jdbcTemplate.query(queryWithInjection, extractor);
    }

    @Override
    public List<Movie> getMoviesByGenre(Integer genre) {
        return jdbcTemplate.query(String.format(GET_MOVIES_GET_GENRE, genre), extractor);
    }

    @Override
    public List<Movie> getPaginatedListOfMovies(int offset, int limit) {
        String queryWithInjection = String.format(GET_PAGINATED_LIST_OF_MOVIES, offset, limit);
        return jdbcTemplate.query(queryWithInjection, extractor);
    }

    @Override
    public List<Movie> getAllMoviesSortedByRating(SortingPattern sortingPattern) {
        String queryWithInjection = String.format(GET_LIST_OF_MOVIES_SORTED_BY_RATING,sortingPattern.toString());
        return jdbcTemplate.query(GET_LIST_OF_MOVIES_SORTED_BY_RATING, extractor);
    }
    @Override
    public List<Movie> getAllMoviesSortedByDate(SortingPattern sortingPattern) {
        String queryWithInjection = String.format(GET_LIST_OF_MOVIES_SORTED_BY_YEAR,sortingPattern.toString());
        return jdbcTemplate.query(GET_LIST_OF_MOVIES_SORTED_BY_RATING, extractor);
    }

}
