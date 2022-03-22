package com.startrip.codebase.domain.category;

import com.startrip.codebase.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    //
    Optional<Category> findCategoriesByDepthandCategoryParentId(Integer depth, Category categoryParaentID);
    Optional<Category> findCategoryByDepth(Integer depth); // 연결 깊이에 따라 조회..? root로 보면 될 것 같긴 한데
\
}
