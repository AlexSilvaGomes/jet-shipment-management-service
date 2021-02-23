package com.jet.peoplemanagement.repository;

import com.jet.peoplemanagement.model.Client;

import java.util.Optional;

public interface ClientRepositoryCustom {

    public Optional<Client> queroMeuCnpj(String cnpj);
}
