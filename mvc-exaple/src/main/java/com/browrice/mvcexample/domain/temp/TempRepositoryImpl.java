package com.browrice.mvcexample.domain.temp;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 어노테이션 필수
public class TempRepositoryImpl implements TempRepository {


    // 인터페이스로 작성한 함수를 구현하시면됩니다.
    @Override
    public Temp save(Temp temp) {
        return null;
    }

    @Override
    public Optional<Temp> findById(Long id) {
        return Optional.empty();
    }
}
