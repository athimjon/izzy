package org.example.izzy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.izzy.model.base.BaseEntity;

import java.util.List;

@Entity
@Table(name = "colour_variants")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ColourVariant extends BaseEntity {

    @Column( nullable = false)
    private String colour;

    @ManyToOne
    Product product;

    @OneToMany
    List<Attachment> images;

    @OneToMany
    List<SizeVariant> sizeVariants;
}
