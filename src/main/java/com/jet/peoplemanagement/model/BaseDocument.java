package com.jet.peoplemanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDocument {

    @Id
    protected String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt = LocalDateTime.now();
}
