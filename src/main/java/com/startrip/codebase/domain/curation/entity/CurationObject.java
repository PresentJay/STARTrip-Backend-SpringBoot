package com.startrip.codebase.domain.curation.entity;

import com.startrip.codebase.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurationObject {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID curationObjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String encodeObject;

    private LocalDateTime createdTime;

    public void setUser(User user) {
        this.user = user;
    }
}
