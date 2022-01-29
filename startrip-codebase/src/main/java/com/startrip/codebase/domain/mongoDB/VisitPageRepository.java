package com.startrip.codebase.domain.mongoDB;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitPageRepository extends MongoRepository<SearchHistory, ObjectId> {

}
