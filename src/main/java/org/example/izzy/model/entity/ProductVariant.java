package org.example.izzy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.izzy.model.base.BaseEntity;
import org.example.izzy.model.enums.Size;

@Entity
@Table(name = "product_variants")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariant extends BaseEntity {
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Colour color;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    @Column(nullable = false)
    private Size size;

//sku (Stock Keeping Unit, unique identifier for each variant, e.g., "SHIRT-WHT-XL-001")
//    @Column(name = "sku", nullable = false, length = 50)
//    private String sku;

}
