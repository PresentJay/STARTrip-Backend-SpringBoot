package com.startrip.codebase.controller;

import com.startrip.codebase.constant.Role;
import com.startrip.codebase.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserController userController;

    @Test
    public void 이용자_저장_테스트() {
        String savedUserEmail = userController.signup("test@t.com");

        assertThat(savedUserEmail).isEqualTo( "test@t.com");
    }
}