package com.khamutov.movieland.config.comparators;

import com.khamutov.movieland.entity.Movie;

import java.util.Comparator;

public class MovieYearComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie m1, Movie m2) {
        return Integer.compare(m1.getYear(), m2.getYear());
    }

}
