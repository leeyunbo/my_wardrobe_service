package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.entity.community.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findCommentsByPostId(Pageable pageable, Long id);

}
