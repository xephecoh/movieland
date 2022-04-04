package com.khamutov.movieland.services;

import com.khamutov.movieland.config.comparators.MoviePriceComparator;
import com.khamutov.movieland.config.comparators.MovieRatingComparator;
import com.khamutov.movieland.config.comparators.MovieYearComparator;
import com.khamutov.movieland.config.pagination.MoviePaginatorResponse;
import com.khamutov.movieland.entity.Movie;
import com.khamutov.movieland.repo.MovieRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final CurrencyRateService currencyRateService;
    private final DecimalFormat numberFormater;



    public MovieService(MovieRepository movieRepository, CurrencyRateService currencyRateService) {
        this.movieRepository = movieRepository;
        this.currencyRateService = currencyRateService;
        numberFormater = new DecimalFormat("#.00");
    }

    public List<Movie> getAllMoviesSortedByPrice() {
        Map<Integer, Movie> movieMap = new HashMap<>();
        double usdRate = Double.parseDouble(currencyRateService.getCurrencyRate("USD", LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getAllMovies();
        allMovies.forEach(movie -> {
            int movieId = movie.getMovieId();
            if (movieMap.get(movieId) != null) {
                movieMap.get(movieId).getGenres().add(movie.getGenre());
            } else {
                movieMap.put(movieId, movie);
                movie.getGenres().add(movie.getGenre());
            }
        });
        movieMap.values().forEach(movie ->
                movie.setPrice(movie.getPrice() / usdRate));
        return new ArrayList<>(movieMap.values())
                .stream()
                .sorted((m1, m2) -> new MoviePriceComparator().reversed().compare(m1, m2))
                .collect(Collectors.toList());
    }

    public MoviePaginatorResponse<Movie> getAllMoviesSortedByPricePaginated(String limit,String offset) {
        Map<Integer, Movie> movieMap = new HashMap<>();
        double usdRate = Double.parseDouble(currencyRateService.getCurrencyRate("USD", LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getAllMovies();
        allMovies.forEach(movie -> {
            int movieId = movie.getMovieId();
            if (movieMap.get(movieId) != null) {
                movieMap.get(movieId).getGenres().add(movie.getGenre());
            } else {
                movieMap.put(movieId, movie);
                movie.getGenres().add(movie.getGenre());
            }
        });
        movieMap.values().forEach(movie ->
                movie.setPrice(movie.getPrice() / usdRate));

        List<Movie> list = new ArrayList<>(movieMap.values())
                .stream()
                .sorted((m1, m2) -> new MoviePriceComparator().reversed().compare(m1, m2))
                .collect(Collectors.toList());

        MoviePaginatorResponse<Movie> paginatorMovieSublist =
                getPaginatorMovieSublist(list, Integer.parseInt(offset), Integer.parseInt(limit));


        return paginatorMovieSublist;
    }

    public List<Movie> getAllMoviesSortedByRating() {
        Map<Integer, Movie> movieMap = new HashMap<>();
        double usdRate = Double.parseDouble(currencyRateService.getCurrencyRate("USD", LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getAllMovies();
        allMovies.forEach(movie -> {
            int movieId = movie.getMovieId();
            if (movieMap.get(movieId) != null) {
                movieMap.get(movieId).getGenres().add(movie.getGenre());
            } else {
                movieMap.put(movieId, movie);
                movie.getGenres().add(movie.getGenre());
            }
        });
        movieMap.values().forEach(movie ->
                movie.setPrice(movie.getPrice() / usdRate));
        return new ArrayList<>(movieMap.values())
                .stream()
                .sorted((m1, m2) -> new MovieRatingComparator().compare(m1, m2))
                .collect(Collectors.toList());
    }

    public List<Movie> getAllMoviesSortedByYear() {
        Map<Integer, Movie> movieMap = new HashMap<>();
        double usdRate = Double.parseDouble(currencyRateService.getCurrencyRate("USD", LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getAllMovies();
        allMovies.forEach(movie -> {
            int movieId = movie.getMovieId();
            if (movieMap.get(movieId) != null) {
                movieMap.get(movieId).getGenres().add(movie.getGenre());
            } else {
                movieMap.put(movieId, movie);
                movie.getGenres().add(movie.getGenre());
            }
        });
        movieMap.values().forEach(movie ->
                movie.setPrice(movie.getPrice() / usdRate));
        return new ArrayList<>(movieMap.values())
                .stream()
                .sorted((m1, m2) -> new MovieYearComparator().compare(m1, m2))
                .collect(Collectors.toList());
    }


    public List<Movie> getRandomMovies(int randomNumber) {
        Map<Integer, Movie> movieMap = new HashMap<>();
        double usdRate = Double.parseDouble(currencyRateService.getCurrencyRate("USD", LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getRandomMovies(randomNumber);
        allMovies.forEach(movie -> {
            int movieId = movie.getMovieId();
            if (movieMap.get(movieId) != null) {
                movieMap.get(movieId).getGenres().add(movie.getGenre());
            } else {
                movieMap.put(movieId, movie);
                movie.getGenres().add(movie.getGenre());
            }
        });

        movieMap.values().forEach(movie ->
                movie.setPrice(movie.getPrice() / usdRate));
        return new ArrayList<>(movieMap.values());
    }

    public List<Movie> getMoviesByGenre(String genre) {
        double usdRate = Double.parseDouble(currencyRateService.getCurrencyRate("USD", LocalDate.now()).getRate());
        Map<Integer, Movie> movieMap = new HashMap<>();
        List<Movie> allMovies = movieRepository.getMoviesByGenre(genre);
        allMovies.forEach(movie -> {
            int movieId = movie.getMovieId();
            if (movieMap.get(movieId) != null) {
                movieMap.get(movieId).getGenres().add(movie.getGenre());
            } else {
                movieMap.put(movieId, movie);
                movie.getGenres().add(movie.getGenre());
            }
        });
        movieMap.values().forEach(movie ->
                movie.setPrice(movie.getPrice() / usdRate));
        return new ArrayList<>(movieMap.values());
    }

    private MoviePaginatorResponse<Movie> getPaginatorMovieSublist(List<Movie> list,
                                                                         int offset,
                                                                         int limit) {
        int from = Math.min(offset, list.size());
        int to = Math.min(limit + offset, list.size());
        return new MoviePaginatorResponse<>(list.subList(from, to), list.size());
    }
}
