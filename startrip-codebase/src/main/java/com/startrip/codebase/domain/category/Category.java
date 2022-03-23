package com.startrip.codebase.domain.category;

import com.startrip.codebase.domain.category.dto.CreateCategoryDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_parent_id")
    private Category categoryParent;

    @Column(name = "category_name", unique = true)
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


    @Builder // id를 제외하여 builder를 적용시킬 것이므로 따로 생성자 위에 builder 패턴을 적용하였음.
    public Category (Category categoryParent, String categoryName, Integer depth){
        this.categoryParent = categoryParent;
        this.categoryName = categoryName;
        this.depth = depth;
    }


    public void setDepth(Integer depth){
        this.depth = depth;
    }

    public void setCategoryName(String categoryName){
        this.categoryName = categoryName;
    }

    public void setCategoryParent(Category categoryParent){
        this.categoryParent = categoryParent;
    }

}
