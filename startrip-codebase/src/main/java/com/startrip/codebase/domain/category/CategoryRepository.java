package com.startrip.codebase.domain.category;

import com.startrip.codebase.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findCategoryByDepth(Integer depth); // 연결 깊이에 따라 조회..? root로 보면 될 것 같긴 한데
    Optional<Category> findByCategoryName(String name); //카테고리 이름으로 조회하기
}
