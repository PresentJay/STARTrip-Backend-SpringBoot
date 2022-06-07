package com.startrip.codebase.curation;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.startrip.codebase.curation.chains.*;
import com.startrip.codebase.domain.curation.entity.CurationObject;
import com.startrip.codebase.domain.curation.repository.CurationObjectRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import com.startrip.codebase.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
public class CurationManager {
    private final JPAQueryFactory jpaQueryFactory;
    private final CurationPipeline pipeline;
    private final CurationObjectRepository curationObjectRepository;
    private final UserRepository userRepository;

    @Autowired
    public CurationManager(JPAQueryFactory jpaQueryFactory, CurationObjectRepository curationObjectRepository, UserRepository userRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.curationObjectRepository = curationObjectRepository;
        this.userRepository = userRepository;

        pipeline = new CurationPipeline<>(new TagCuration());
    }

    public CurationObject start(CurationInputObject userSource) {
        try {
            pipeline.execute(userSource);
            return curationObjectSerializeAndPersist(userSource);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null; // null?
    }

    private CurationObject curationObjectSerializeAndPersist(CurationInputObject object) throws IOException {
        String userEmail = getUserEmail();
        log.debug(userEmail + " SecurityContext");

        User user = findUser(userEmail);
        String encode = curationObjectSerialize(object);

        CurationObject resultObject = CurationObject.builder()
                .curationObjectId(UUID.randomUUID())
                .createdTime(LocalDateTime.now())
                .user(user)
                .encodeObject(encode)
                .build();

        return curationObjectRepository.save(resultObject);
    }

    private User findUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new RuntimeException("없는 유저");
        });
        return user;
    }

    private String getUserEmail() {
        return SecurityUtil.getCurrentUsername().orElseThrow(() -> {
            throw new RuntimeException("SecurityContextHolder에 유저 정보가 없습니다.");
        });
    }

    private String curationObjectSerialize(CurationInputObject object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(object);
        byte[] serializeSource;
        serializeSource = bos.toByteArray();
        return Base64.getEncoder().encodeToString(serializeSource);
    }
}
