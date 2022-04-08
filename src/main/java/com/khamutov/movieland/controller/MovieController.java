package com.khamutov.movieland.controller;


import com.khamutov.movieland.entity.Currency;
import com.khamutov.movieland.entity.Movie;
import com.khamutov.movieland.entity.Order;
import com.khamutov.movieland.entity.SortingPattern;
import com.khamutov.movieland.services.MovieServiceImplementation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieServiceImplementation movieServiceImplementation;

    @GetMapping
    List<Movie> getAllMoviesSortedByPrice(@RequestParam("currency") Currency currency) {
        return movieServiceImplementation.getAllMoviesSortedByPrice(currency);
    }

    @GetMapping("paginate")
    List<Movie> getAllMoviesSortedByPricePaginated(@RequestParam Integer limit,
                                                   @RequestParam Integer offset,
                                                   @RequestParam("currency") Currency currency) {
        return movieServiceImplementation.getAllMoviesSortedByPricePaginated(limit, offset, currency);
    }

    @GetMapping("sort")
    List<Movie> getAllMoviesSortedByRating(@RequestParam("sortingpattern") SortingPattern sortingPattern,
                                           @RequestParam("order") Order order,
                                           @RequestParam("currency") Currency currency) {
        if (sortingPattern == SortingPattern.RATING) {
            return movieServiceImplementation.getAllMoviesSortedByRating(order, currency);
        } else {
            return movieServiceImplementation.getAllMoviesSortedByYear(sortingPattern, currency);
        }
    }


    @GetMapping("random")
    List<Movie> getAllMovies(@Value("${random}") Integer random,
                             @RequestParam("currency") Currency currency) {
        return movieServiceImplementation.getRandomMovies(random, currency);
    }

    @GetMapping("genre")
    List<Movie> getMoviesByGenre(@RequestParam Integer genre,
                                 @RequestParam("currency") Currency currency) {
        return movieServiceImplementation.getMoviesByGenre(genre, currency);
    }

    @GetMapping("create")
    void createMovie(@RequestParam String movieName,
                     @RequestParam String description,
                     @RequestParam (required=false,name="price")Double price,
                     @RequestParam Integer year,
                     @RequestParam Double rating,
                     @RequestParam("genres") String[] genres) {
        movieServiceImplementation.createMovie(movieName, description, price, year, rating, Arrays.asList(genres));
    }

}
