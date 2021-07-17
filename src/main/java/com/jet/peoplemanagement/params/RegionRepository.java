package com.jet.peoplemanagement.params;


import com.jet.peoplemanagement.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RegionRepository extends MongoRepository<Region, String> {

    List<Region> findByClientId(String clientId);

}
