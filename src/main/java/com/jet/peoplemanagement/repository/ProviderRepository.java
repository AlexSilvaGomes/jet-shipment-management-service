package com.jet.peoplemanagement.repository;


import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.model.Provider;
import com.jet.peoplemanagement.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProviderRepository extends MongoRepository<Provider, String> {
    Optional<Provider> findByEmail(String email);
    //List<Tutorial> findByPublished(boolean published);
  //List<Tutorial> findByTitleContaining(String title);
}
