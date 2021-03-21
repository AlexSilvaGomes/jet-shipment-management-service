package com.jet.peoplemanagement.user;

import com.jet.peoplemanagement.model.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
@Document(collection = "users")
public class JetUser extends BaseDocument {
    private String username;
    private  String password;
    private String type;
}
