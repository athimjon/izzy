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
@Table(name = "size_variants")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SizeVariant extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Size size;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    private ColourVariant colourVariant;

}
