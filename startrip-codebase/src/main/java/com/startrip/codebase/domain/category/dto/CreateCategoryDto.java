package com.startrip.codebase.domain.category.dto;


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
}
