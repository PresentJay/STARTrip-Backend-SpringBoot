package com.startrip.codebase.domain.curation;

import com.startrip.codebase.domain.user.User;
import lombok.AllArgsConstructor;
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
public class CurationObject {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID curationObjectId;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private Byte object;

    private LocalDateTime createdTime;
}
