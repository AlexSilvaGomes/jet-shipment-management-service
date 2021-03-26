package com.jet.peoplemanagement.repository;

import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.model.UserProfile;

import java.util.Optional;

public interface ClientRepositoryCustom {

    public Optional<Client> queroMeuCnpj(String cnpj);
    public Optional<Client> queroByEmail(String cnpj);

}
