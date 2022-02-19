package com.startrip.codebase.domain.mongoDB;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
@NoRepositoryBean
public interface VisitPageRepository extends MongoRepository<SearchHistory, ObjectId> {

}
