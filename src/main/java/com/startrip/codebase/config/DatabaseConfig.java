 package com.startrip.codebase.config;

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.startrip.codebase.domain.mongoDB" )
@EnableMongoAuditing
public class DatabaseConfig {
    // aplication 상으로는 로컬로 연결되어있기 때문에 mongoDB를 로컬에서 준비하지 않았다면 관련 로그가 뜰 것입니다.
    // 따라서 이러한 로그는 뒤로하고 docker에서 mongoDB가 준비된다면 수정되어야 합니다.
    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(mongoClient, "mongostartrip");
        return new MongoTemplate(factory);
    }
}