package com.browrice.mvcexample.service;

import com.browrice.mvcexample.domain.temp.TempRepository;
import com.browrice.mvcexample.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 필수
public class TempService {

    private final TempRepository tempRepository;

    @Autowired
    public TempService(TempRepository tempRepository) {
        this.tempRepository = tempRepository;
    }


    // 아래부터 비즈니스 로직을 작성하시면 됩니다.

    ...

}
