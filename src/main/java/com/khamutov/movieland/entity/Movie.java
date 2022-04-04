package com.khamutov.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Movie implements Serializable {
    @JsonIgnore
    private int movieId;
    private String movieName;
    private int year;
    private String county;
    private String description;
    private double rating;
    private double price;
    private Set<String> genres = new HashSet<>();
    @JsonIgnore
    private String genre;

    public Movie(int movieId,
                 String country,
                 String description,
                 String movieName,
                 double price,
                 double rating,
                 int year,
                 String genre) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.year = year;
        this.county = country;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.genre= genre;
    }
}
