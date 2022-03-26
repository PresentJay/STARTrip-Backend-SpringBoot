package com.startrip.codebase.domain.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Setter
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.REMOVE) // TODO : 삭제 시 에러 발생함
    @JoinColumn(name = "category_parent_id")
    private Category categoryParent;

    @Setter
    @Column(name = "category_name", unique = true) // TODO:undefined할 때 unique를 삭제 할지 고려할 것.
    private String categoryName;

    @Setter
    @Column(nullable = false)
    private Integer depth;

    //dto -> Entity
    public static Category createCategory(CreateCategoryDto dto) {
        // createDto에 있던 categoryParentId는 여기서 Entity로 바꾸지 않는다.
        Category category = Category.builder()
                .categoryName(dto.getCategoryName())
                .build();
        return category;
    }

    @Builder // id를 제외하여 builder를 적용시킬 것이므로 따로 생성자 위에 builder 패턴을 적용하였음.
    public Category (Category categoryParent, String categoryName, Integer depth){
        this.categoryParent = categoryParent;
        this.categoryName = categoryName;
        this.depth = depth;
    }
}
