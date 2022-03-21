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
@ToString
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId; // PK

    @Column(name = "category_parent_id")
    private Long categoryParentID;

    @Column(name = "category_name")
    private String categoryName;

    //자식 카테고리들
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryParentID", cascade = CascadeType.PERSIST)
    @Column (name ="category_child_id")
    private List<Category> categoryChildId = new ArrayList<>();

    @Column(nullable = false)
    private Integer depth;

    public void update(CreateCategoryDto dto){
        this.categoryParentID = dto.getCategoryParentId();
        this.categoryId = dto.getCategoryId();
        this.categoryName = dto.getCategoryName();
        this.depth = dto.getDepth();
    }

}
