package com.jet.peoplemanagement.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Document(collection = "providers")
public class Provider extends BaseDocument {

    @NotBlank
    private String name;

    private String mei;

    @NotBlank
    @Indexed(unique = true, name = "emailIndex")
    private String email;

    @Length(min = 11, max = 11)
    @NotBlank(message = "cellphone is mandatory")
    private String cellphone;

    @NotBlank
    private String contactPhone;

    @NotBlank
    private String zone;

    @NotBlank
    private String level;

    @NotBlank
    private String type;

    @NotBlank
    private String bank;

    @NotBlank
    private String agency;

    @NotBlank
    private String account;

    @NotBlank
    @Indexed(unique = true, name = "cpfIndex")
    private String cpf;

    private boolean activated;
}
