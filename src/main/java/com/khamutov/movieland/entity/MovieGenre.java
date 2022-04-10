package com.khamutov.movieland.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieGenre {
    int movieId;
    int genreId;
}
