package com.startrip.codebase.domain.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCategoryDto {

    private Long categoryParentId;
    private String categoryName;
    private Integer depth;
}