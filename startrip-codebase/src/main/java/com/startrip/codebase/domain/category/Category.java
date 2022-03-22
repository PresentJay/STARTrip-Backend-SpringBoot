package com.startrip.codebase.domain.category;

import com.startrip.codebase.domain.category.dto.CreateCategoryDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "category_id")
    private Category categoryParent;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(nullable = false)
    private Integer depth;


    //dto -> Entity
    public static Category createCategory(CreateCategoryDto dto) {
        // createDto에 있던 categoryParentId는 여기서 Entity로 바꾸지 않는다.
        Category category = Category.builder()
                .categoryName(dto.getCategoryName())
                .depth(dto.getDepth())
                .build();
        return category;
    }

}
