package com.khamutov.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;



@Data
@AllArgsConstructor
@Builder
public class Genre implements Serializable {
    @JsonIgnore
    private int genreId;
    private String genre;
}
