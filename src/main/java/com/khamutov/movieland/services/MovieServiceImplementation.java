package com.khamutov.movieland.services;


import com.khamutov.movieland.entity.Currency;
import com.khamutov.movieland.entity.Movie;
import com.khamutov.movieland.repo.MovieRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class MovieServiceImplementation {
    private final MovieRepository movieRepository;
    private final CurrencyRateService currencyRateService;

    public MovieServiceImplementation(MovieRepository movieRepository, CurrencyRateService currencyRateService) {
        this.movieRepository = movieRepository;
        this.currencyRateService = currencyRateService;

    }

    public List<Movie> getAllMoviesSortedByPrice(Currency currency) {
        double currencyRate = Double.parseDouble(currencyRateService.getCurrencyRate(currency, LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getAllMovies();
        allMovies.forEach(movie -> movie.setPrice(movie.getPrice() * currencyRate));
        return allMovies;
    }

    public List<Movie> getAllMoviesSortedByPricePaginated(Integer limit, Integer offset, Currency currency) {
        double currencyRate = Double.parseDouble(currencyRateService.getCurrencyRate(currency, LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getPaginatedListOfMovies(limit, offset);
        allMovies.forEach(movie -> movie.setPrice(movie.getPrice() * currencyRate));
        return allMovies;
    }

    public List<Movie> getAllMoviesSortedByRating(String rating, Currency currency) {
        double currencyRate = Double.parseDouble(currencyRateService.getCurrencyRate(currency, LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getAllMoviesSortedByRating(rating);
        allMovies.forEach(movie -> movie.setPrice(movie.getPrice() * currencyRate));
        return allMovies;
    }

    public List<Movie> getAllMoviesSortedByYear(String date,Currency currency) {
        double usdRate = Double.parseDouble(currencyRateService.getCurrencyRate(currency, LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getAllMoviesSortedByDate(date);
        allMovies.forEach(movie -> movie.setPrice(movie.getPrice() * usdRate));
        return allMovies;
    }


    public List<Movie> getRandomMovies(int randomNumber,Currency currency) {
        double usdRate = Double.parseDouble(currencyRateService.getCurrencyRate(currency, LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getRandomMovies(randomNumber);
        allMovies.forEach(movie -> movie.setPrice(movie.getPrice() / usdRate));
        return allMovies;
    }

    public List<Movie> getMoviesByGenre(Integer genre,Currency currency) {
        double usdRate = Double.parseDouble(currencyRateService.getCurrencyRate(currency, LocalDate.now()).getRate());
        List<Movie> allMovies = movieRepository.getMoviesByGenre(genre);
        allMovies.forEach(movie -> movie.setPrice(movie.getPrice() / usdRate));
        return allMovies;
    }


}
