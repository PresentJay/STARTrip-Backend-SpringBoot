package com.browrice.mvcexample.domain.temp;

import java.util.Optional;

public interface TempRepository {

    // DB를 어떻게 관리하고, 저장할지 생각해서 함수 작성하시면됩니다.
    Temp save(Temp temp);

    Optional<Temp> findById(Long id);

    ...


}
