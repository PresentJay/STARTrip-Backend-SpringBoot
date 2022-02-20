package com.startrip.codebase.domain.mongoDB;


import lombok.*;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "searchHistory")
@ToString
public class SearchHistory {

    @Id
    private Long id;
    private String userId;
    private String searchContent;
    private Date timeStamp;

    @Builder
    public SearchHistory(Long id, String userId, String searchContent , Date timeStamp){
        this.id = id;
        this.userId = userId;
        this.searchContent = searchContent;
        this.timeStamp = timeStamp;
    }
}
