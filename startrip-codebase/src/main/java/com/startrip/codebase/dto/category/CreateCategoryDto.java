package com.startrip.codebase.dto.category;


import com.startrip.codebase.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryDto {

    private Long categoryId;
    private Long categoryParentId;
    private String categoryName;
    private Integer depth;

    public CreateCategoryDto(Category entity){
        this.categoryId = entity.getCategoryId();
        this.categoryName = entity.getCategoryName();
        this.depth = entity.getDepth();

        // 최초의, rootCategory 생성시엔 NullPointer 에러가 날 수 있다.
        if(entity.getCategoryParent() == null)
            this.categoryParentId = 0L; // 최초로, parent가 없는 거라면 0값을 가지도록 했음
        else
            this.categoryParentId = entity.getCategoryParent().getCategoryId(); // 부모 id가 있다면 정상적으로 받기
    }

    public Category createCategory(){
        Category category = Category.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .depth(depth)
                .build();
        return category;
    }
}
