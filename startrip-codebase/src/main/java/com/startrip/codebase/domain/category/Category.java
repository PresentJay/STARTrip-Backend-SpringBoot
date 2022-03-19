package com.startrip.codebase.domain.category;

import com.sun.istack.NotNull;
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

    @ManyToOne(fetch = FetchType.EAGER) //최상위 카테고리
    @JoinColumn (name ="category_root_id")
    private Category categoryRootId;

    @ManyToOne(fetch = FetchType.EAGER) //상위 카테고리
    @JoinColumn (name ="category_parent_id")
    private Category categoryParentId;

    @Column(name = "category_name")
    private String categoryName;

    //자식 카테고리들
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryParentId", cascade = CascadeType.PERSIST)
    @JoinColumn (name ="child_Categories")
    private List<Category> childCategories = new ArrayList<>();


    @Column(nullable = false)
    private Integer depth;

    /*
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category_up_id")
    private List<Category> children; // 자식 */
}
