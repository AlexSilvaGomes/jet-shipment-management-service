package com.jet.peoplemanagement.params;

import com.jet.peoplemanagement.model.BaseDocument;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Document(collection = "regions")
@Data
public class Region extends BaseDocument {

    @NotBlank
    private String clientId;

    @NotBlank
    public String name;

    @NotNull
    public Double price;
}


