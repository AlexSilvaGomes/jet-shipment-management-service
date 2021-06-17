package com.jet.peoplemanagement.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
public class SimpleMongoConfig {

    public static final String COL_SHIPMENTS = "shipments";
    public static final String CLIENTS = "clients";
    public static final String PROVIDER = "providers";
    public static final String UPLOADED_FILES = "uploadedFiles";
    public static final String DELIVERIES = "deliveries";
    public static final String FAST_JET_DB = "fast-jet-db";
    public static final String PROVIDER_DELIVERY_CPF = "providerDeliveryCpf";

    @Value("${mongo.connection.string}")
    private String mongoConnection;

    @Value("${mongo.db.name}")
    private String mongoDatabaseName;

    @Bean
    public MongoClient mongo() {
        String fullConnectionString = mongoConnection + "/" + mongoDatabaseName + "?authSource=admin";
        ConnectionString connectionString = new ConnectionString(fullConnectionString);

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo(), mongoDatabaseName);

        ((MappingMongoConverter)mongoTemplate.getConverter())
                .setTypeMapper(new DefaultMongoTypeMapper(null));//removes _class

        mongoTemplate.indexOps(PROVIDER).ensureIndex(new Index("cpf", Sort.Direction.ASC).unique());
        mongoTemplate.indexOps(PROVIDER).ensureIndex(new Index("email", Sort.Direction.ASC).unique());

        mongoTemplate.indexOps(CLIENTS).ensureIndex(new Index("cnpj", Sort.Direction.ASC).unique());
        mongoTemplate.indexOps(CLIENTS).ensureIndex(new Index("email", Sort.Direction.ASC).unique());

        mongoTemplate.indexOps(COL_SHIPMENTS).ensureIndex(new Index("shipmentCode", Sort.Direction.ASC).unique());
        mongoTemplate.indexOps(COL_SHIPMENTS).ensureIndex(new Index("saleCode", Sort.Direction.ASC).unique());
        mongoTemplate.indexOps(COL_SHIPMENTS).ensureIndex(new Index("client", Sort.Direction.ASC));
        mongoTemplate.indexOps(COL_SHIPMENTS).ensureIndex(new Index("currentProviderId", Sort.Direction.ASC));

        mongoTemplate.indexOps(UPLOADED_FILES).ensureIndex(new Index("shipmentCode", Sort.Direction.ASC).named("uploadedFilesShipmentIndex"));
        mongoTemplate.indexOps(UPLOADED_FILES).ensureIndex(new Index("client", Sort.Direction.ASC));

        mongoTemplate.indexOps(DELIVERIES).ensureIndex(new Index("shipmentCode", Sort.Direction.ASC).named("deliveryShipmentIndex"));
        mongoTemplate.indexOps(DELIVERIES).ensureIndex(new Index(PROVIDER_DELIVERY_CPF, Sort.Direction.ASC));
        //mongoTemplate.indexOps(DELIVERIES).ensureIndex(new Index("providerConferenceCpf", Sort.Direction.ASC));
        //mongoTemplate.indexOps(DELIVERIES).ensureIndex(new Index("shipment", Sort.Direction.ASC));


//        MongoJsonSchema schema = MongoJsonSchemaCreator.create(mongoTemplate.getConverter()).createSchemaFor(Client.class);
//        mongoTemplate.createCollection(Client.class, CollectionOptions.empty().schema(schema));

        return mongoTemplate;
    }


}