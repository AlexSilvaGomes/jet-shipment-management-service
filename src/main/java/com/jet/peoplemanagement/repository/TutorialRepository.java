package com.jet.peoplemanagement.repository;


import com.jet.peoplemanagement.model.Tutorial;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TutorialRepository extends MongoRepository<Tutorial, String> {
  List<Tutorial> findByPublished(boolean published);
  List<Tutorial> findByTitleContaining(String title);
}
