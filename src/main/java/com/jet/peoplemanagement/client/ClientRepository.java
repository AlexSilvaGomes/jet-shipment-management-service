package com.jet.peoplemanagement.client;


import com.jet.peoplemanagement.model.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String>, ClientRepositoryCustom {
    Client findByEmail(String email);
    List<Client> findByCnpjLike(String cnpj);
    Optional<Client> findFirstByCompanyNameLike(String companyName);

    @Query(value = "{'companyName': {$regex : ?0, $options: 'i'}}")
    List<Client> findByCompanyRegexIgnoreCase(String companyName,  Pageable pageable);
}
