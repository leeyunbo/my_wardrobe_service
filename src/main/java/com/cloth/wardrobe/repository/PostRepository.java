package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.entity.community.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
