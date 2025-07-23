package org.example.izzy.repo;

import org.example.izzy.model.entity.SizeVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SizeVariantRepository extends JpaRepository<SizeVariant, UUID> {
}