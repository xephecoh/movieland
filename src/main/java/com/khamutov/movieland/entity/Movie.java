package com.khamutov.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.Set;


@Data
@Builder
public class Movie implements Serializable {
    @JsonIgnore
    private int movieId;
    private String movieName;
    private int year;
    private String county;
    private String description;
    private double rating;
    private double price;
    private Set<Genre> genres;
    @JsonIgnore
    private Genre genre;
}
