package com.khamutov.movieland.controller;

import com.khamutov.movieland.config.pagination.MoviePaginatorResponse;
import com.khamutov.movieland.entity.Movie;
import com.khamutov.movieland.services.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("{currency}")
    List<Movie> getAllMoviesSortedByPrice(@PathVariable String currency) {
        return movieService.getAllMoviesSortedByPrice(currency);
    }

    @PostMapping
    List<Movie> getAllMoviesSortedByPricePaginated(@RequestParam Integer limit,
                                                   @RequestParam Integer offset,
                                                   @RequestParam String currency) {
        return movieService.getAllMoviesSortedByPricePaginated(limit, offset, currency);
    }

    @GetMapping
    List<Movie> getAllMoviesSortedByRating(@RequestParam String rating) {
        return movieService.getAllMoviesSortedByRating(rating);
    }

    @GetMapping("y")
    List<Movie> getAllMoviesSortedByYear() {
        return movieService.getAllMoviesSortedByYear();
    }

    @GetMapping("random")
    List<Movie> getAllMovies(@Value("${random}") Integer random) {
        return movieService.getRandomMovies(random);
    }

    @RequestMapping("genre/{genre}")
    List<Movie> getMoviesByGenre(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre);
    }

}
