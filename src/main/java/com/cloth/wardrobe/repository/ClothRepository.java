package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.domain.clothes.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothRepository extends JpaRepository<Cloth, Long> {
}
