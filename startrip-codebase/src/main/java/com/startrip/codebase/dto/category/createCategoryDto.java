package com.startrip.codebase.dto.category;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class createCategoryDto {

    private Long categoryId;
    private Long categoryParenId;
    private String categoryName;
    private Integer depth;


}
