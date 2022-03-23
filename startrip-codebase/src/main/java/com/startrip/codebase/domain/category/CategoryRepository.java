package com.startrip.codebase.domain.category;

import com.startrip.codebase.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Long타입 id로 Category 조회하기
    Optional<Category> findById (Long id);

}
