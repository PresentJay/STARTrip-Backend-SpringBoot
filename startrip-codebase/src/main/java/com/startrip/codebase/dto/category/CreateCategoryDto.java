package com.startrip.codebase.dto.category;

import com.startrip.codebase.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryDto {

    private Long categoryParentId;
    private String categoryName;

    public CreateCategoryDto (Category entity) {
        this.categoryName = entity.getCategoryName();

        //parent가 최초의 대분류일 경우를 처리하기 위함 (depth=2)
        if (entity.getCategoryParent() == null) {
            this.categoryParentId = (long)1;  // id가 1인 것은 root다.
        } else {
            this.categoryParentId = entity.getCategoryParent().getId();
        }
    }
}