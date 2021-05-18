package com.jet.peoplemanagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Document(collection = "providers")
@TypeAlias("Provider")
public class Provider extends BaseDocument implements UserProfile {

    public Provider(String providerId) {
        this.id = providerId;
    }

    public Provider() {}

    @NotBlank
    private String name;
    private String mei;
    private String img;

    @NotBlank
    @Indexed(unique = true, name = "emailIndex")
    private String email;

    @Length(min = 11, max = 11)
    @NotBlank(message = "cellphone is mandatory")
    private String mobile;

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

    private String address;

    @NotBlank
    @Indexed(unique = true, name = "cpfIndex")
    private String cpf;

    private String cep;

    private boolean activated;

    @NotNull
    private LocalDateTime birthDate;


}
