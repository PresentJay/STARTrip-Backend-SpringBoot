package com.startrip.codebase.domain.category;

import com.startrip.codebase.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findCategoryByCategoryName (String categoryName);
    List<Category> findAllByCategoryParent(Category categoryParent);
    List<Category> findAllByDepthAndCategoryParent(Integer depth, Category category);

    @Query("select max(c.depth) as max_depth from Category c")
    Integer findByDepthMax();
}