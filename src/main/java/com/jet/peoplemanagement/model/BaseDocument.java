package com.jet.peoplemanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public class BaseDocument {

    @Id
    protected String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Override
    public String toString() {
        ObjectMapper object=new ObjectMapper();
        try {
            return object.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Erro convertendo model para json Veja: {}", e.getMessage());
        }
        return "";
    }

}
