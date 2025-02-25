package com.ead.course.dtos;

import com.ead.course.utils.CustomPageable;
import com.ead.course.utils.CustomSort;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ResponsePageDTO<T> extends PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ResponsePageDTO(
            @JsonProperty("content") List<T> content,
            @JsonProperty("pageable") CustomPageable pageable,
            @JsonProperty("last") boolean last,
            @JsonProperty("totalPages") int totalPages,
            @JsonProperty("totalElements") long totalElements,
            @JsonProperty("first") boolean first,
            @JsonProperty("size") int size,
            @JsonProperty("number") int number,
            @JsonProperty("sort") CustomSort sort,
            @JsonProperty("numberOfElements") int numberOfElements,
            @JsonProperty("empty") boolean empty){

        super(content, PageRequest.of(number, size), totalElements);
    }

    public ResponsePageDTO(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ResponsePageDTO(List<T> content) {
        super(content);
    }
}
