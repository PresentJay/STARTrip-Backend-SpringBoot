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
    private String user_id;
    private String searchContent;
    private Date timeStamp;

    @Builder
    public SearchHistory(Long id, String user_id, String searchContent , Date timeStamp){
        this.id = id;
        this.user_id = user_id;
        this.searchContent = searchContent;
        this.timeStamp = timeStamp;
    }
}
