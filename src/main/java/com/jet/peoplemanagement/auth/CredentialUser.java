package com.jet.peoplemanagement.auth;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

public class CredentialUser extends User {

    private String type;

    public CredentialUser(String username, String password, String type) {
        super(username, password, Collections.emptyList());
        this.type = type;
    }

    public CredentialUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
