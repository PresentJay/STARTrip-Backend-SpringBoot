package com.startrip.codebase.domain.curation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.startrip.codebase.curation.CurationManager;
import com.startrip.codebase.curation.CurationObjectSource;
import com.startrip.codebase.curation.chains.ChainType;
import com.startrip.codebase.domain.curation.entity.CurationObject;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class CurationObjectRepositoryTest {

    @Autowired
    private CurationObjectRepository curationObjectRepository;

    @Autowired
    private UserRepository userRepository;

    void createUser() {
        User user = User.builder()
                .nickname("test")
                .name("test")
                .email("test@test.com")
                .password("1234")
                .build();
        userRepository.save(user);
    }

    @DisplayName("큐레이션 오브젝트 저장 테스트")
    @Test
    void test01() throws IOException, ClassNotFoundException {
        createUser();
         User user = userRepository.findByEmail("test@test.com").orElseThrow(() -> {
             throw new RuntimeException("없는 유저");
         });
        CurationObjectSource source = new CurationObjectSource();
        source.addInput(ChainType.TAG, new String("미술관"));

        // 직렬화
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(source);
        byte[] serializeSource;
        serializeSource = bos.toByteArray();
        String encode = Base64.getEncoder().encodeToString(serializeSource);

        CurationObject object = CurationObject.builder()
                .curationObjectId(UUID.randomUUID())
                .createdTime(LocalDateTime.now())
                .user(user)
                .encodeObject(encode)
                .build();

        curationObjectRepository.save(object);

        // 역직렬화
        byte[] serializedSource = Base64.getDecoder().decode(object.getEncodeObject());
        ByteArrayInputStream bais = new ByteArrayInputStream(serializedSource);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object objectSource = ois.readObject();
        CurationObjectSource deSerialized = (CurationObjectSource) objectSource;

        System.out.println("역직렬화된 Object : " + deSerialized);
        assertEquals(source.getData(ChainType.TAG), deSerialized.getData(ChainType.TAG));
    }
}