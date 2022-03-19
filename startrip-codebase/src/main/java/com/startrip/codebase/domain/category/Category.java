package com.startrip.codebase.domain.category;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @NotNull
    private String name;

    @NotNull
    private Integer depth;

    // 순환 참조
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "category_id", insertable=false, updatable=false)
//    private Category category_up_id; // 부모
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category_up_id")
//    private List<Category> children; // 자식

    public void update(UpdateCategoryDto dto) {
        this.name = dto.getName();
        this.depth = dto.getDepth();
    }

    @Getter
    @Setter
    public static class UpdateCategoryDto {
        private String name;

        private Integer depth;

    }
}
