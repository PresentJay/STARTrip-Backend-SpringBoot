package com.startrip.codebase.domain.mongoDB;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "visitPage")
@ToString
public class VisitPage {

    @Id
    private Long id;
    private String ip;
    private String visitUrl;
    private Date timeStamp;

    @Builder
    public VisitPage(Long id, String ip, String visitUrl, Date timeStamp){
        this.id = id;
        this.ip = ip;
        this.visitUrl = visitUrl;
        this.timeStamp = timeStamp;
    }
}
