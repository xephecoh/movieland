package com.khamutov.movieland.entity;

import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Genre implements Serializable {
    private Long genreId;
    private String genre;
    private Set<Movie> movies = new HashSet<>();
}
