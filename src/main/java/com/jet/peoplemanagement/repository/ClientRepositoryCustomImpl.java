package com.jet.peoplemanagement.repository;

import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.model.GroupedAggregation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.swing.text.Document;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Slf4j
public class ClientRepositoryCustomImpl implements ClientRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<Client> findById(String id) {
        Client entity = mongoTemplate.findById(id, Client.class);
        return Optional.of(entity);
    }

    public Optional<Client> queroMeuCnpj(String cnpj) {

        Query query = new Query();
        query.addCriteria(Criteria.where("cnpj").is(cnpj));
        Client entity = mongoTemplate.findOne(query, Client.class);
        return Optional.of(entity);
    }


    public Optional<Client> queroByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        Client entity = mongoTemplate.findOne(query, Client.class);
        return Optional.of(entity);
    }


    public void testAggregation(){
        GroupOperation groupByStateAndSumPop = group("state").sum("pop").as("statePop");
        MatchOperation filterStates = match(new Criteria("activated").is(true));
        SortOperation sortByPopDesc = sort(Sort.by(Sort.Direction.DESC, "name"));

        Aggregation aggregation = newAggregation(filterStates, sortByPopDesc);

        AggregationResults<Client> result = mongoTemplate.aggregate(aggregation, "clients", Client.class);
        log.info("Result {}", result);
    }

    public void testAggregationShip(){
        GroupOperation groupByStateAndSumPop = group("zone").sum("shipmentCode").as("shipmentCount");
        MatchOperation filterStates = match(new Criteria("status").is("postado"));
        SortOperation sortByPopDesc = sort(Sort.by(Sort.Direction.DESC, "zone"));

        Aggregation aggregation = newAggregation(groupByStateAndSumPop, filterStates, sortByPopDesc);

        AggregationResults<GroupedAggregation> result = mongoTemplate.aggregate(aggregation, "shipments", GroupedAggregation.class);
        log.info("Result {}", result.getRawResults());
    }

}
