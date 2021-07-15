package com.jet.peoplemanagement.shipment;

import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.model.GroupedAggregation;
import com.jet.peoplemanagement.shipmentStatus.DeliveryStatusEnum;
import com.jet.peoplemanagement.util.ExcelGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Slf4j
public class ShipmentRepositoryCustomImpl implements ShipmentRepositoryCustom {

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

    /*private Query getQuery(RouteLog routeLog) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(routeLog.getIp())) {
            criteria.and("ip").is(routeLog.getIp());
        }
        if (StringUtils.isNotBlank(routeLog.getTargetServer())) {
            criteria.and("targetServer").is(routeLog.getTargetServer());
        }
        if (StringUtils.isNotBlank(routeLog.getRequestMethod())) {
            criteria.and("requestMethod").is(routeLog.getRequestMethod().toUpperCase());
        }
        if (StringUtils.isNotBlank(routeLog.getCreateTimeFrom())
                && StringUtils.isNotBlank(routeLog.getCreateTimeTo())) {
            criteria.andOperator(
                    Criteria.where("createTime").gt(routeLog.getCreateTimeFrom()),
                    Criteria.where("createTime").lt(routeLog.getCreateTimeTo())
            );
        }
        query.addCriteria(criteria);
        return query;
    }*/



    @Override
    public Page<Shipment> getShipmentsByParamsPageable(ShipmentFilter filter) {
        Query query = new Query();
        Criteria criteria = new Criteria();

        final Pageable pageable = PageRequest.of(Objects.isNull(filter.getPageNumber()) ? 0 : filter.getPageNumber(),
                Objects.isNull(filter.getPageSize()) ? 5 : filter.getPageSize(),
                Sort.Direction.DESC,
                "updatedAt");

        query.with(pageable);
        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
        setupCriteria(filter, criteria);
        query.addCriteria(criteria);
        List<Shipment> shipments = mongoTemplate.find(query, Shipment.class);

        return PageableExecutionUtils.getPage(
                shipments,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Shipment.class));
    }

    @Override
    public List<Shipment> getShipmentsByParams(ShipmentFilter filter) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
        setupCriteria(filter, criteria);
        query.addCriteria(criteria);
        List<Shipment> shipments = mongoTemplate.find(query, Shipment.class);
        return shipments;
    }

    private void setupCriteria(ShipmentFilter filter, Criteria criteria) {
        if (!Objects.isNull(filter.getInitDate()) && !Objects.isNull(filter.getEndDate())) {
            filter.getInitDate().atZone(ZoneId.of("America/Sao_Paulo"));
            filter.getEndDate().atZone(ZoneId.of("America/Sao_Paulo"));
            LocalDateTime initDate = filter.getInitDate().with(LocalTime.of(0, 0));
            LocalDateTime endDate = filter.getEndDate().with(LocalTime.of(23, 59, 59));
            criteria.andOperator(
                    Criteria.where("createdAt").gte(initDate),
                    Criteria.where("createdAt").lte(endDate)
            );
        }

        if(StringUtils.isNotEmpty(filter.getClientId())){
            criteria.and("clientId").is(filter.getClientId());
        }

        if(filter.getStatus() != null){
            criteria.and("status").is(filter.getStatus());
        }

        if(StringUtils.isNotEmpty(filter.getShipmentCode())){
            criteria.and("shipmentCode").regex(".*"+ filter.getShipmentCode()+".*", "i");
        }
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
