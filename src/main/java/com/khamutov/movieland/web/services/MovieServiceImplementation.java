package com.khamutov.movieland.web.services;


import com.khamutov.movieland.entity.Currency;
import com.khamutov.movieland.entity.Movie;
import com.khamutov.movieland.entity.Order;
import com.khamutov.movieland.entity.SortingPattern;
import com.khamutov.movieland.web.repo.soringjdbc.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImplementation {
    private final MovieRepository movieRepository;
    private final CurrencyRateService currencyRateService;


    public List<Movie> getAllMoviesSortedByPrice(Currency currency) {
        double currencyRate = Double.parseDouble(currencyRateService.getCurrencyRate(currency, LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getAllMovies();
        allMovies.forEach(movie -> movie.setPrice(movie.getPrice() / currencyRate));
        return allMovies;
    }

    public List<Movie> getAllMoviesSortedByPricePaginated(Integer limit, Integer offset, Currency currency) {
        double currencyRate = Double.parseDouble(currencyRateService.getCurrencyRate(currency, LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getPaginatedListOfMovies(limit, offset);
        allMovies.forEach(movie -> movie.setPrice(movie.getPrice() * currencyRate));
        return allMovies;
    }

    public List<Movie> getAllMoviesSortedByRating(Order sortingPattern, Currency currency) {
        double currencyRate = Double.parseDouble(currencyRateService.getCurrencyRate(currency, LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getAllMoviesSortedByRating(sortingPattern);
        allMovies.forEach(movie -> movie.setPrice(movie.getPrice() * currencyRate));
        return allMovies;
    }

    public List<Movie> getAllMoviesSortedByYear(SortingPattern sortingPattern, Currency currency) {
        double currencyRate = Double.parseDouble(currencyRateService.getCurrencyRate(currency, LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getAllMoviesSortedByDate(sortingPattern);
        allMovies.forEach(movie -> movie.setPrice(movie.getPrice() * currencyRate));
        return allMovies;
    }

    public List<Movie> getRandomMovies(int randomNumber, Currency currency) {
        double usdRate = Double.parseDouble(currencyRateService.getCurrencyRate(currency, LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getRandomMovies(randomNumber);
        allMovies.forEach(movie -> movie.setPrice(movie.getPrice() / usdRate));
        return allMovies;
    }

    public List<Movie> getMoviesByGenre(Integer genre, Currency currency) {
        double usdRate = Double.parseDouble(currencyRateService.getCurrencyRate(currency, LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getMoviesByGenre(genre);
        allMovies.forEach(movie -> movie.setPrice(movie.getPrice() / usdRate));
        return allMovies;
    }


    public void createMovie(String movieName,
                            String description,
                            double price,
                            int year,
                            double rating,
                            List<String> genres) {
        movieRepository.createMovie(movieName, description, price, year, rating, genres);
    }
}
