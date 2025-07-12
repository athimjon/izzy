package org.example.izzy.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.izzy.model.base.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn
    private Category parent;

    @OneToOne
    @JoinColumn(nullable = false)
    private Attachment attachment;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Category> children = new HashSet<>();
}
