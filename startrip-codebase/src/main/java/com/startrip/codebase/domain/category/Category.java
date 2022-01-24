package com.startrip.codebase.domain.category;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Category {
    @Id
    private Integer category_id; // PK

    @NotNull
    private String category_name;

    @NotNull
    private Integer depth;

    // 순환 참조
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", insertable=false, updatable=false)
    private Category category_up_id; // 부모

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category_up_id")
    private List<Category> children; // 자식
}
