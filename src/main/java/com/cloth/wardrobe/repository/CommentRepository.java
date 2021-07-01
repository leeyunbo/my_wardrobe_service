package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.domain.community.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
