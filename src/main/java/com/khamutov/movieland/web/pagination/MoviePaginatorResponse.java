package com.khamutov.movieland.web.pagination;



import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MoviePaginatorResponse<T> {
    @JsonProperty(value = "items")
    private List<T> items;
    @JsonProperty(value = "itemsTotal")
    private int itemsTotal;



}

