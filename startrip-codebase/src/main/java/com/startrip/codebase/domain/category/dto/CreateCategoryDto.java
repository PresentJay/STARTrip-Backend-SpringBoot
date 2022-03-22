package com.startrip.codebase.domain.category.dto;


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
    private Integer depth;

    public CreateCategoryDto (Category entity) {
        this.depth = entity.getDepth();
        this.categoryName = entity.getCategoryName();

        //parent가 최초의 대분류일 경우를 처리하기 위함
        if (entity.getCategoryParent() == null) {
            this.categoryParentId = 0L;
        } else {
            this.categoryParentId = entity.getCategoryParent().getId();
        }
    }
}