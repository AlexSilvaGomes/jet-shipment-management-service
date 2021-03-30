package com.jet.peoplemanagement.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Document(collection = "clients")
public class Client extends BaseDocument implements UserProfile {

    public Client(String id) {
        this.id = id;
    }
    public Client(){ }

    @NotBlank(message = "Company name is mandatory")
    private String companyName;

    @NotBlank
    @Indexed(unique = true)
    private String cnpj;

    @NotBlank
    private String name;

    @NotBlank
    @Indexed(unique = true)
    private String email;

    @Length(min = 11, max = 11)
    @NotBlank(message = "cellphone is mandatory")
    private String mobile;

    private String contactPhone;
    private  String address;
    private String img;
    private String cep;
    private boolean activated;
}
