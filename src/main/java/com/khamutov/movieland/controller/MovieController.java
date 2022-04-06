package com.khamutov.movieland.controller;


import com.khamutov.movieland.entity.Currency;
import com.khamutov.movieland.entity.Movie;
import com.khamutov.movieland.services.MovieServiceImplementation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieServiceImplementation movieServiceImplementation;

    @GetMapping()
    List<Movie> getAllMoviesSortedByPrice(@RequestParam ("currency") Currency currency) {
        return movieServiceImplementation.getAllMoviesSortedByPrice(currency);
    }

    @PostMapping
    List<Movie> getAllMoviesSortedByPricePaginated(@RequestParam Integer limit,
                                                   @RequestParam Integer offset,
                                                   @RequestParam ("currency") Currency currency) {
        return movieServiceImplementation.getAllMoviesSortedByPricePaginated(limit, offset, currency);
    }

    @GetMapping
    List<Movie> getAllMoviesSortedByRating(@RequestParam String rating,
                                           @RequestParam ("currency") Currency currency) {
        return movieServiceImplementation.getAllMoviesSortedByRating(rating,currency);
    }

    @GetMapping
    List<Movie> getAllMoviesSortedByYear(@RequestParam String date,
                                         @RequestParam ("currency") Currency currency) {
        return movieServiceImplementation.getAllMoviesSortedByYear(date,currency);
    }

    @GetMapping("random")
    List<Movie> getAllMovies(@Value("${random}") Integer random,
                             @RequestParam ("currency") Currency currency) {
        return movieServiceImplementation.getRandomMovies(random, currency);
    }

    @GetMapping("genre")
    List<Movie> getMoviesByGenre(@RequestParam Integer genre,
                                 @RequestParam ("currency") Currency currency) {
        return movieServiceImplementation.getMoviesByGenre(genre, currency);
    }

}
