package com.startrip.codebase.domain.mongoDB;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableMongoRepositories
@NoRepositoryBean
public interface SearchHistoryRepository extends MongoRepository<SearchHistory, String> {
    public SearchHistory findbySearchContent(String searchContent);
    public List<SearchHistory> findByUserId(String userId);
}
