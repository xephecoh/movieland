package com.khamutov.movieland.entity;


import lombok.*;

import java.io.Serializable;
import java.util.List;


@Data
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class User implements Serializable {
    private Long userId;
    private String userName;
    private String email;
    private String password;
    private List<Review> reviews;
}
