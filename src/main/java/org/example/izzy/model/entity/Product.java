package org.example.izzy.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.izzy.model.base.BaseEntity;
import org.example.izzy.model.enums.Gender;
import org.example.izzy.model.enums.ProductStatus;

@Entity
@Table(name = "products")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    private Integer discount;

    @Column(nullable = false)
    private Gender gender;

    @Builder.Default
    @Column(nullable = false)
    private ProductStatus status = ProductStatus.NEW;

    @Column(columnDefinition = "TEXT" ,nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;
}
