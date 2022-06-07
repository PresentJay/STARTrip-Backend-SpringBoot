package com.startrip.codebase.domain.curation.entity;

import com.startrip.codebase.curation.CurationInputObject;
import com.startrip.codebase.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.util.Base64;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob // Binary Data
    @NotNull
    private String encodeObject;

    private LocalDateTime createdTime;

    public void setUser(User user) {
        this.user = user;
    }

    public CurationObject deSerialize() throws IOException, ClassNotFoundException {
        byte[] serializedSource = Base64.getDecoder().decode(this.getEncodeObject());
        ByteArrayInputStream bais = new ByteArrayInputStream(serializedSource);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object objectSource = ois.readObject();
        CurationInputObject deSerialized = (CurationInputObject) objectSource;
    }
}
