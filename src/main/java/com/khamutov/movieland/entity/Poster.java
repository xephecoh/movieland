package com.khamutov.movieland.entity;

import lombok.*;

import java.io.Serializable;


@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Data
public class Poster implements Serializable {
    private int posterId;
    private String movieName;
    private String link;
}
