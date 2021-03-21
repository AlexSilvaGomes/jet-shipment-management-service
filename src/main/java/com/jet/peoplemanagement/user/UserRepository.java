package com.jet.peoplemanagement.user;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<JetUser, String> {
    Optional<JetUser> getByUsername(String username);
}
