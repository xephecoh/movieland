package com.khamutov.movieland.controller;

import com.khamutov.movieland.config.pagination.MoviePaginatorResponse;
import com.khamutov.movieland.entity.Movie;
import com.khamutov.movieland.services.CurrencyRateService;
import com.khamutov.movieland.services.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/movies")
public class MovieController {

    private final MovieService movieService;


    public MovieController(MovieService movieService, CurrencyRateService currencyRateService) {
        this.movieService = movieService;
    }

    @RequestMapping
    List<Movie> getAllMoviesSortedByPrice() {
        return movieService.getAllMoviesSortedByPrice();
    }

    @PostMapping
    MoviePaginatorResponse<Movie> getAllMoviesSortedByPricePaginated(@RequestParam String limit,
                                                                     @RequestParam String offset) {
        return movieService.getAllMoviesSortedByPricePaginated(limit,offset);
    }
    @RequestMapping("getAllMoviesSortedByRating")
    List<Movie> getAllMoviesSortedByRating() {
        return movieService.getAllMoviesSortedByRating();
    }
    @RequestMapping("getAllMoviesSortedByYear")
    List<Movie> getAllMoviesSortedByYear() {
        return movieService.getAllMoviesSortedByYear();
    }

    @RequestMapping("getRandomMovies/{randomNumber}")
    List<Movie> getAllMovies(@PathVariable String randomNumber) {
        Integer integer = Integer.parseInt(randomNumber);
        return movieService.getRandomMovies(integer);
    }

    @RequestMapping("getMoviesByGenre/{genre}")
    List<Movie> getMoviesByGenre(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre);
    }

}
