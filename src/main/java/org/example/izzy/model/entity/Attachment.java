package org.example.izzy.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.izzy.model.base.BaseEntity;

@Entity
@Table(name = "attachments")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class Attachment extends BaseEntity {


    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private String contentType;

}
