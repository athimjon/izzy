package org.example.izzy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.izzy.model.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "colour_variants")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ColourVariant extends BaseEntity {

    @Column(nullable = false)
    private String colourName;

    @ManyToOne
    Product product;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "colour_variant_id")
    List<Attachment> images = new ArrayList<>();

    @OneToMany(mappedBy = "colourVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SizeVariant> sizeVariants = new ArrayList<>();
}
