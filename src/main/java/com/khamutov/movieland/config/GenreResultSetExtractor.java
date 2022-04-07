package com.khamutov.movieland.config;


import com.khamutov.movieland.entity.Genre;
import com.khamutov.movieland.entity.Movie;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class GenreResultSetExtractor implements ResultSetExtractor<Genre> {

    @Override
    public Genre extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Genre genre = null;
        while (resultSet.next()) {
            genre = Genre.builder()
                    .genreId(resultSet.getInt("genre_id"))
                    .genre(resultSet.getString("genre"))
                    .build();
        }
        return genre;
    }
}
