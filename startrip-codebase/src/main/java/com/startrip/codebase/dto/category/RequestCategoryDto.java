package com.startrip.codebase.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RequestCategoryDto {
    private UUID categoryParentId;
    private String categoryName;
}