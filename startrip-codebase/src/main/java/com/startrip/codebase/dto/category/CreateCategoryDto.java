package com.startrip.codebase.dto.category;

import com.startrip.codebase.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryDto {
    private UUID categoryParentId;
    private String categoryName;
}