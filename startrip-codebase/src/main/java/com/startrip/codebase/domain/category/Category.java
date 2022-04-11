package com.startrip.codebase.domain.category;

import com.startrip.codebase.dto.category.RequestCategoryDto;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.REMOVE)
    @JoinColumn(name = "category_parent_id", nullable = true)
    private Category categoryParent;

    @Setter
    @Column(name = "category_name", unique = true)
    private String categoryName;

    @Column(nullable = false)
    private Integer depth;

    public static Category createCategory(RequestCategoryDto dto) {
        Category category = Category.builder()
                .categoryName(dto.getCategoryName())
                .build();
        return category;
    }

    @Builder
    public Category (Category categoryParent, String categoryName, Integer depth){
        this.categoryParent = categoryParent;
        this.categoryName = categoryName;
        this.depth = depth;
    }
}