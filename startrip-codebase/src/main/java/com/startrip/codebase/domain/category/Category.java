package com.startrip.codebase.domain.category;

import com.startrip.codebase.dto.category.CreateCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"Category\"")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId; // PK

    @ManyToOne(fetch = FetchType.EAGER) //상위 카테고리
    @JoinColumn (name ="category_parent")
    private Category categoryParent;

    @Column(name = "category_name")
    private String categoryName;

    //자식 카테고리들
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryParent", cascade = CascadeType.PERSIST)
    @JoinColumn (name ="category_child_id")
    private List<Category> categoryChildId = new ArrayList<>();


    @Column(nullable = false)
    private Integer depth;


    /*
    public Category(Category categoryParent, String categoryName, Integer depth){
        this.categoryParent = categoryParent;
        this.categoryName = categoryName;
        this.depth = depth;
    } */


}
