package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.domain.s3.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
