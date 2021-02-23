package com.jet.peoplemanagement.repository;


import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.model.Provider;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProviderRepository extends MongoRepository<Provider, String> {
  //List<Tutorial> findByPublished(boolean published);
  //List<Tutorial> findByTitleContaining(String title);
}
