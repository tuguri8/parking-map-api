package com.bamplee.chomi.api.infrastructure.persistence.jpa.repository;

import com.bamplee.chomi.api.infrastructure.persistence.jpa.entity.Air;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirRepository extends JpaRepository<Air, Long> {
    Optional<Air> findTopByAreaNameOrderByIdDesc(String areaName);
}
