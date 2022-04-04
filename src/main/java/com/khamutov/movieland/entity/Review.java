package com.khamutov.movieland.entity;

import lombok.*;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Review implements Serializable {
    private Long reviewId;
    private String review;
    private String userName;
    private String movieName;
}
