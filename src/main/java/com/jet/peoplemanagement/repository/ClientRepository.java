package com.jet.peoplemanagement.repository;


import com.jet.peoplemanagement.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String>, ClientRepositoryCustom {
    Client findByEmail(String email);
}
