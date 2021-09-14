package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.entity.common.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
