package com.startrip.codebase.domain.category;

import com.startrip.codebase.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findCategoryByDepth(Integer depth);
}
