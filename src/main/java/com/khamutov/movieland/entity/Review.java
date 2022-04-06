package com.khamutov.movieland.entity;

import lombok.*;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Review implements Serializable {
    private int reviewId;
    private String review;
    private User user;
    private Movie movie;
}
