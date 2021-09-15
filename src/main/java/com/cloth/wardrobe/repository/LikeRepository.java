package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.entity.community.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMember_IdAndPost_Id(Long memberId, Long postId);
}
