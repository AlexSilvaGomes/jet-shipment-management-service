package com.jet.peoplemanagement.auth;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String token;
    private final String userType;
    private final  String username;
    private final String name;
    private final String id;
    private final String cpf;
}