package com.khamutov.movieland.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Set;


@Data
@AllArgsConstructor
public class Genre implements Serializable {
    private Long genreId;
    private String genre;
    private Set<Movie> movies;
}
