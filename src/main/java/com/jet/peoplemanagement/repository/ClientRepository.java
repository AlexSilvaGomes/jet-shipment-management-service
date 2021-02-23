package com.jet.peoplemanagement.repository;


import com.jet.peoplemanagement.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String>, ClientRepositoryCustom {
  //List<Tutorial> findByPublished(boolean published);
  //List<Tutorial> findByTitleContaining(String title);
}
