package org.example.izzy.repo;

import org.example.izzy.model.dto.response.admin.AdminColourVariantRes;
import org.example.izzy.model.entity.ColourVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ColourVariantRepository extends JpaRepository<ColourVariant, UUID> {
    List<ColourVariant> findByProductId(UUID productId);
}