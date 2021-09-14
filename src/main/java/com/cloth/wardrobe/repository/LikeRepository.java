package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.entity.community.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMember_IdAndWardrobe_Id(Long memberId, Long wardrobeId);

    Optional<Like> findByMember_IdAndCloth_Id(Long memberId, Long clothId);

    Optional<Like> findByMember_IdAndRecord_Id(Long memberId, Long recordId);

}
