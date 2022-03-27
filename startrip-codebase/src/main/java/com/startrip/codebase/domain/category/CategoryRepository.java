package com.startrip.codebase.domain.category;

import com.startrip.codebase.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByCategoryParent(Category categoryParent);
    Optional<Category> findCategoryByCategoryName (String categoryName);


    // 캍고리를, 이름과 엡스로 찾기
    List<Category> findAllByDepthAndCategoryParent(Integer depth, Category category);

    Boolean existsByCategoryParent(Category categoryParent); //없는지 조회하기 위함

    @Query("select max(c.depth) as max_depth from Category c")
    Integer findByDepthMax();
}
