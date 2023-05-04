package com.example.config;

import com.example.security.JwtAspect;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.listComponents")
public class MongoDatabaseConfig {
    @Value ("${spring.mongo.connection-string}")
    String connectionString;
    @Value("${spring.mongo.database-name}")
    String databaseName;
    private static final Logger log = LoggerFactory.getLogger (JwtAspect.class);
    @Bean
    public MongoClient mongoClient() {
        log.info (connectionString);
        return MongoClients.create(connectionString);
    }
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate (mongoClient(), databaseName);
    }

}
